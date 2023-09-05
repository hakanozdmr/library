package hakanozdmr.library.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;

import static ch.qos.logback.core.spi.ComponentTracker.DEFAULT_TIMEOUT;

public class CacheManager implements CacheClient {
    private final JedisPool pool;
    private boolean failOnError = true;
    public CacheManager(String host, int port, boolean failOnError, JedisPoolConfig poolConfig) {
        this.failOnError = failOnError;
        pool = new JedisPool(poolConfig, host, port, DEFAULT_TIMEOUT,failOnError);
    }

    private String serializeObject(Object value) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Veriyi JSON'a dönüştürme hatası: " + e.getMessage(), e);
        }
    }
    @Override
    public void set(String key, Object value) {
        try (Jedis jedis = pool.getResource()) {
            // Önceki değeri temizleme (varsa) ve yeni değeri ayarlama
            jedis.set(key, serializeObject(value));
        } catch (JedisException e) {
            // Hata yönetimi: Redis'e erişim sırasında bir hata oluşursa burada işleyebilirsiniz.
            if (failOnError) {
                throw new RuntimeException("Redis'e erişim hatası: " + e.getMessage(), e);
            } else {
                // Hata yakalanırsa loglama veya başka bir işlem yapabilirsiniz.
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object get(String key) {
        try(Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    @Override
    public void delete(String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.del(key);
        }
    }

    @Override
    public void deleteAll(List<String> keys) {
        int retry = 0;
        try (Jedis jedis = pool.getResource()) {
            while (retry < 3) {
                try {
                    jedis.del(keys.toArray(new String[0]));
                    break;
                } catch (Exception e) {
                    retry++;
                }
            }
        }
    }

    @Override
    public void shutdown() {
        pool.close();
    }
}
