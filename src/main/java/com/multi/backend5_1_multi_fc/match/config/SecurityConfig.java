// Spring Security 설정
package com.multi.backend5_1_multi_fc.match.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // POST, PUT, DELETE 허용 위해 비활성화
                .cors().and()       // 다른 출처(프론트) 허용
                .authorizeRequests()
                .antMatchers(
                        "/",
                        "/api/**",       // REST API 전체 허용
                        "/ws/**",        // 웹소켓 허용
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/favicon.ico"
                ).permitAll()
                .anyRequest().permitAll();

        return http.build();
    }
}
