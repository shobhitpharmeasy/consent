package com.pharmeast.consent.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {

        http.csrf( AbstractHttpConfigurer::disable )  // Disabling CSRF
            .authorizeHttpRequests( ( authorize ) -> {
                authorize.anyRequest().authenticated();
            } ).httpBasic( Customizer.withDefaults() );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {

        UserDetails user = User.builder().username( "consent" ).password(
            passwordEncoder().encode( "consent" ) ).roles( "USER" ).build();

        UserDetails admin = User.builder().username( "admin" ).password(
            passwordEncoder().encode( "admin" ) ).roles( "ADMIN" ).build();

        return new InMemoryUserDetailsManager( user, admin );
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}

//package com.pharmeast.consent.config;
//
//import com.pharmeast.consent.exception.CustomAccessDeniedHandler;
//import com.pharmeast.consent.utils.HashUtils;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.ProviderManager;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.List;
//
//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity
//@RequiredArgsConstructor
//public class SpringSecurityConfig {
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain( HttpSecurity http ) throws Exception {
//
//        http.csrf( AbstractHttpConfigurer::disable )  // Disable CSRF
//            .authorizeHttpRequests( auth -> auth.requestMatchers( "/api/login" )
//                                                .permitAll().requestMatchers(
//                    "/api/employees/**" ).hasAnyRole( "ADMIN", "USER" ).anyRequest()
//                                                .authenticated() ).sessionManagement(
//                session -> session.sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
/// /            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
//            .httpBasic( Customizer.withDefaults() ).exceptionHandling(
//                ex -> ex.accessDeniedHandler( new CustomAccessDeniedHandler() ) );
//        return http.build();
//    }
//
//    // @TODO: Swagger api for spring api
//    // @TODO: isDeleted for soft delete , hard delete
//    // @TODO: locking- optimistic - versioning of rows  & pessimistic locking
//    // @TODO:  for rollbacks -pessimistic locking - row wise lock
//    // @TODO: add these locks when giving consents
//    // @TODO: logging and remove auth
//
//    @Bean
//    public AuthenticationManager authenticationManager(
//        AuthenticationConfiguration authenticationConfiguration
//    ) {
//
//        return new ProviderManager( List.of(
////                authenticationProvider(), // DB authentication provider
//            inMemoryAuthenticationProvider()
//            // In-memory authentication for admin
//        ) );
//    }
//
//    @Bean
//    public AuthenticationProvider inMemoryAuthenticationProvider() {
//
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(
//            inMemoryUserDetailsService() );
//        provider.setPasswordEncoder( passwordEncoder() );
//        return provider;
//    }
//
//    // Define an in-memory user for the admin
//    @Bean
//    public UserDetailsService inMemoryUserDetailsService() {
//
//        UserDetails admin = User.builder().username( "admin" ).password(
//                                    passwordEncoder().encode( "admin" ) ) // Hardcoded password
//                                .roles( "ADMIN" ).build();
//
//        return new InMemoryUserDetailsManager( admin );
//    }
//
////    @Bean
////    public AuthenticationProvider authenticationProvider() {
////        DaoAuthenticationProvider provider =
////            new DaoAuthenticationProvider(userDetailsService);
////        provider.setPasswordEncoder(passwordEncoder());
////        return provider;
////    }
//
//    @Bean
//    public static PasswordEncoder passwordEncoder() {
//
//        return HashUtils.passwordEncoder();
//    }
//}
