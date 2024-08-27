package co.istad.mbanking.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder);
        return auth;
    }

    @Bean
    SecurityFilterChain configFilterChain(HttpSecurity http) throws Exception {

        // Protect routes
        /*http.authorizeHttpRequests(endpoint -> endpoint
                .requestMatchers(HttpMethod.GET, "/api/v1/card-types").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/account-types").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/users").hasRole("MANAGER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users").hasAnyRole("MANAGER", "ADMIN")
                .anyRequest().authenticated()
        );*/

        /*http.authorizeHttpRequests(endpoint -> endpoint
                .anyRequest().authenticated());*/

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
