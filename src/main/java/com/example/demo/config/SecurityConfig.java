package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain; 
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter; 

@Configuration
@EnableWebSecurity 
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Define as regras de acesso e configura o filtro JWT
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita o CSRF (Cross-Site Request Forgery) porque a API é stateless
            .csrf(csrf -> csrf.disable())

            // Configura as regras de autorização
            .authorizeHttpRequests(auth -> auth
                // Permite acesso irrestrito aos endpoints de Autenticação e H2 Console
                .requestMatchers("/api/auth/**", "/h2-console/**").permitAll() 

                // Exige autenticação para qualquer outro request (incluindo /api/tasks)
                .anyRequest().authenticated()
            )

            // Configura a política de sessão como STATELESS (sem sessão de servidor)
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            // Adiciona o nosso filtro JWT antes do filtro padrão de Username/Password
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        // Necessário para o H2 Console funcionar com Spring Security
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}