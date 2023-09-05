package hakanozdmr.library.config;


import hakanozdmr.library.security.JWTAccessDeniedHandler;
import hakanozdmr.library.security.JwtAuthenticationEntryPoint;
import hakanozdmr.library.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;
    private final JWTAccessDeniedHandler accessDeniedHandler;

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .headers().frameOptions().disable().and()
                .csrf().disable()
                .cors().and()
//                .authorizeHttpRequests()
//                .requestMatchers( new AntPathRequestMatcher("/api/public"))
//                .permitAll()
//                .requestMatchers( new AntPathRequestMatcher("/h2-console/**"))
//                .permitAll()
//                .requestMatchers( new AntPathRequestMatcher("/api/auth/login"))
//                .permitAll()
//                .requestMatchers( new AntPathRequestMatcher("/api/auth/signup"))
//                .permitAll()
//                .requestMatchers( new AntPathRequestMatcher("/**"))
//                .permitAll()
//                .requestMatchers(
//
//                        "/api/public",
//                        "/h2-console/**",
//                        "/api/auth/login",
//                        "/api/auth/signup",
//                        "/**"
////                        "/api/v1/auth/**",
////                        "/v2/api-docs",
////                        "/v3/api-docs",
////                        "/v3/api-docs/**",
////                        "/swagger-resources",
////                        "/swagger-resources/**",
////                        "/configuration/ui",
////                        "/configuration/security",
////                        "/swagger-ui/**",
////                        "/webjars/**",
////                        "/swagger-ui.html"
//                ).permitAll()
                .authorizeRequests(auth -> {
                    auth.requestMatchers( new AntPathRequestMatcher("/api/public"))
                            .permitAll()
                            .requestMatchers( new AntPathRequestMatcher("/h2-console/**"))
                            .permitAll()
                            .requestMatchers( new AntPathRequestMatcher("/api/auth/login"))
                            .permitAll()
                            .requestMatchers( new AntPathRequestMatcher("/api/auth/signup"))
                            .permitAll()
                            .requestMatchers( new AntPathRequestMatcher("/**"))
                            .permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/admin")).hasAuthority("ADMIN");
                    auth.requestMatchers(new AntPathRequestMatcher("/api/user")).hasAnyAuthority("ADMIN", "USER");
                    auth.anyRequest().authenticated();

                })
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web) -> web.ignoring().requestMatchers("/api/public", "/h2-console/**", "/api/auth/login","/api/auth/signup","/**");
//    }
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }
}

