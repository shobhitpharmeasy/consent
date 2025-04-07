package com.pharmeasy.consent.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

// TODO: 2 types of locking - Pessimistic Locking & Optimistic/Sequence Locking
// TODO: Spring security how to break an api & how to secure it

@Slf4j
@Configuration
@EnableMethodSecurity
public class SpringSecurityConfig {

    @Bean
    public static PasswordEncoder passwordEncoder() {
        final PasswordEncoder passwordEncoder = com.pharmeasy.consent.utils.HashUtils.passwordEncoder();
        log.info("Password Encoder bean created of class : {}", passwordEncoder.getClass().getName());
        return passwordEncoder;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring SecurityFilterChain with basic HTTP auth and CSRF disabled");

        http.csrf(AbstractHttpConfigurer::disable)  // Disabling CSRF
            .authorizeHttpRequests(authorize -> authorize.requestMatchers("/api/**")
                                                         .permitAll()  // Allow anyone on any path under /api
                                                         .anyRequest().authenticated()
                                   // All other requests require authentication
            ).httpBasic(Customizer.withDefaults());

        log.info("SecurityFilterChain configured successfully");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        log.info("Creating in-memory user details service");

        UserDetails user = User.builder().username("consent").password(passwordEncoder.encode("consent")).roles("USER")
                               .build();
        log.debug("In-memory user created with username: {}", user.getUsername());

        UserDetails admin = User.builder().username("admin").password(passwordEncoder.encode("admin")).roles("ADMIN")
                                .build();
        log.debug("In-memory admin created with username: {}", admin.getUsername());

        log.info(
            "InMemoryUserDetailsManager bean initialized with users: {}, {}", user.getUsername(), admin.getUsername());
        return new InMemoryUserDetailsManager(user, admin);
    }
}
