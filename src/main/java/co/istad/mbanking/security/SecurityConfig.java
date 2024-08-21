package co.istad.mbanking.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain configFilterChain(HttpSecurity http) throws Exception {

        // Protect routes
        http.authorizeHttpRequests(endpoint -> endpoint
                .anyRequest().authenticated()
        );

        // Security Mechanism
        http.httpBasic(Customizer.withDefaults());

        // Disable CSRF Token
        http.csrf(token -> token.disable());

        // Make API stateless
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
