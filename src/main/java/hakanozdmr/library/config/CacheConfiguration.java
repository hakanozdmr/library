package hakanozdmr.library.config;

import hakanozdmr.library.service.CacheClient;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

    @Override
    public KeyGenerator keyGenerator() {
        return super.keyGenerator();
    }

    @Bean(destroyMethod = "shutdown")
    public CacheClient cacheClient() {
        String type = "redis";
        String host = "127.0.0.1";
        String port = "6379";

        return new CacheClient.Builder()
                .type(type)
                .host(host)
                .port(port)
                .failOnError(false)
                .build();
    }
}
