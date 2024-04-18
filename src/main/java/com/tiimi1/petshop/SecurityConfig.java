package com.tiimi1.petshop;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Katkelma teoksesta
    // Full Stack Development with Spring Boot 3 and React
    // Juha Hinkula
    // Tämä saattaa olla tekijänoikeussuojattua aineistoa.
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).cors(withDefaults())
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests.anyRequest().permitAll());
        return http.build();
    }

    // Katkelma teoksesta
    // Full Stack Development with Spring Boot 3 and React
    // Juha Hinkula
    // Tämä saattaa olla tekijänoikeussuojattua aineistoa.
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
