package com.example.chatting_websocket.config;

import com.example.chatting_websocket.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 보호 비활성화 (필요하면 활성화)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/index.html", "/app.js", "/favicon.ico", "/gs-guide-websocket").permitAll() // 인증 없이 허용
                        .anyRequest().authenticated());

        return http.build();
    }
}
