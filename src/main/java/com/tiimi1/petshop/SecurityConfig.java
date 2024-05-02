package com.tiimi1.petshop;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.tiimi1.petshop.service.UserDetailsServiceImpl;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;

    public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, AuthenticationFilter authenticationFilter, 
            AuthEntryPoint exeptionHandler) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authenticationFilter = authenticationFilter;
        this.exceptionHandler = exeptionHandler;
    }

    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    // Order(1) is compeletely for Thymeleaf. 
    @Bean
    @Order(1)
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).cors(withDefaults())
                .securityMatcher("/admin/**", "/login/**", "/logout")
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers("/")
                        .permitAll()
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated())
                .formLogin(formlogin -> formlogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/admin/home", true)
                        .permitAll())
                .logout(logout -> logout
                        .permitAll());
        return http.build();
    }

    // Order(2) is compeletely for front end.
    // uncomment marked line to open all api endpoints (also comment out @RepositoryRestResource(exported = false) from CustomerRepository to access customers in rest)
    @Bean
    @Order(2)
    SecurityFilterChain filterChainRest(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()).cors(withDefaults())
                .securityMatcher("/api/**", "/signup", "/customerlogin", "/logget/**", "/all/**") // filters what endpoints are handled in this method.
                .sessionManagement(
                    (sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                    .requestMatchers("/api/**").permitAll() // uncomment this to see localhost:8080/api  
                    .requestMatchers(HttpMethod.POST, "/signup", "/customerlogin").permitAll()   // endpoints for signing up and login
                    .requestMatchers(HttpMethod.GET, "/api/products/**", "/api/manufacturers/**", "/all/**").permitAll()  // endpoints for customers without login
                    .requestMatchers("/api/customers/**", "/logget/**").permitAll().anyRequest().authenticated())  // endpoints for logged in customers 
                    .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(exceptionHandler)); 
                return http.build();
    }


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


// Katkelmia teoksesta
// Full Stack Development with Spring Boot 3 and React
// Juha Hinkula
// Tämä saattaa olla tekijänoikeussuojattua aineistoa.
