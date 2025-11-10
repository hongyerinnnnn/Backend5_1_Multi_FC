package com.multi.backend5_1_multi_fc.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // [중요] 회원가입 API 접근을 허용하기 위한 최소한의 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // 테스트를 위해 잠시 끔
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/signup", "/", "/login").permitAll() // 이 주소는 로그인 없이 접속 허용
                        .anyRequest().authenticated() // 나머지는 로그인 해야 접속 가능
                );
        return http.build();
    }
}