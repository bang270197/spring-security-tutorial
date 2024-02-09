package com.test.learningsercurity.security;

import com.test.learningsercurity.filter.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

        @Bean
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    private static CorsConfigurationSource getConfigurationSource(){
        var corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedMethods(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000/", "http://localhost:8080"));
        corsConfiguration.setAllowedHeaders(List.of("Content-Type"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return  source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(corsConfig->corsConfig.configurationSource(getConfigurationSource()));

        // Disable CSRF
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        // Http Request Filter
        httpSecurity.authorizeHttpRequests(
                requestMatcher ->
                        requestMatcher.anyRequest().permitAll()

//                                requestMatchers("/api/auth/login/**").permitAll()
//                                .requestMatchers("/api/auth/sign-up/**").permitAll()
//                                .requestMatchers("/api/auth/verify-token/**").permitAll()
//                                .anyRequest().authenticated()
        );

        httpSecurity.addFilter(new CustomAuthenticationFilter());



        // Authentication Entry Point -> Exception Handler
//        httpSecurity.exceptionHandling(
//                exceptionConfig -> exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
//        );

        // Set session policy = STATELESS
        httpSecurity.sessionManagement(
                sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        // Add JWT Authentication Filter
//        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }


}
