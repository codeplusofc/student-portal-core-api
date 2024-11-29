package br.com.student.portal.securityConfig;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Novo método para desativar CSRF
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Permite acesso a todas as rotas
                );
        return http.build();
    }
}