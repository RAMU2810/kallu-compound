package com.kallucompound;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;

import com.kallucompound.service.AdminUserDetailsService;

@Configuration
public class SecurityConfig {

    private final AdminUserDetailsService adminUserDetailsService;

    public SecurityConfig(
            AdminUserDetailsService adminUserDetailsService) {

        this.adminUserDetailsService =
                adminUserDetailsService;
    }


    // PASSWORD ENCODER
    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    // AUTHENTICATION PROVIDER
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(
                        adminUserDetailsService
                );

        provider.setPasswordEncoder(
                passwordEncoder()
        );

        return provider;
    }


    // AUTHENTICATION MANAGER
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration)
            throws Exception {

        return configuration.getAuthenticationManager();
    }


    // SESSION SECURITY CONTEXT
    @Bean
    public SecurityContextRepository securityContextRepository() {

        return new HttpSessionSecurityContextRepository();
    }


    // SECURITY CONFIGURATION
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

            // CORS
            .cors(cors -> {})

            // CSRF disabled for REST API
            .csrf(csrf -> csrf.disable())

            // STORE LOGIN IN HTTP SESSION
            .securityContext(context ->
                context
                    .securityContextRepository(
                        securityContextRepository()
                    )
                    .requireExplicitSave(true)
            )

            // AUTHORIZATION
            .authorizeHttpRequests(auth -> auth

                // CORS preflight
                .requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
                ).permitAll()


                // PUBLIC TODDY DATA
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/toddy"
                ).permitAll()


                // CUSTOMER REGISTRATION
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/customers"
                ).permitAll()


                // ADMIN CUSTOMER DATA
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/customers"
                ).authenticated()


                // PUBLIC CUSTOMER NAMES
                // Used by feedback dropdown
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/customers/names"
                ).permitAll()


                // CUSTOMER FEEDBACK
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/feedback"
                ).permitAll()


                // ADMIN LOGIN
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/admin/login"
                ).permitAll()


                // ADMIN LOGOUT
                .requestMatchers(
                    HttpMethod.POST,
                    "/api/admin/logout"
                ).permitAll()


                // ADMIN FEEDBACK DATA
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/feedback"
                ).authenticated()


                // ADMIN PRICE / AVAILABILITY
                .requestMatchers(
                    HttpMethod.PUT,
                    "/api/toddy/**"
                ).authenticated()


                // ADMIN SESSION CHECK
                .requestMatchers(
                    HttpMethod.GET,
                    "/api/admin/check"
                ).authenticated()


                // EVERYTHING ELSE
                .anyRequest().authenticated()
            );


        return http.build();
    }
}