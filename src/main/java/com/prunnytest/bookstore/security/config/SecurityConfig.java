package com.prunnytest.bookstore.security.config;

import com.prunnytest.bookstore.security.exceptions.CustomAccessDeniedHandler;
import com.prunnytest.bookstore.security.filter.JWTAuthenticationFilter;
import com.prunnytest.bookstore.security.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.prunnytest.bookstore.model.enums.Permission.*;
import static com.prunnytest.bookstore.model.enums.Roles.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    private final String[] WHITE_LIST = {
            "login/**",
            "register/**",
            "api/v1/author/**",
            "api/v1/genre/**",
            "swagger-ui.html",
            "v2/api-docs/**",
            "v3/api-docs/**",
            "swagger-ui/**",
            "webjars/**"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers(WHITE_LIST)
                                .permitAll()

                                .requestMatchers("/basic/**").hasAnyRole(ADMIN.name(), BASIC.name(), PREMIUM.name(), MANAGER.name())
                                .requestMatchers("/premium/**").hasAnyRole(ADMIN.name(), MANAGER.name(), PREMIUM.name())

                                .requestMatchers(GET, "management/**").hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .requestMatchers(POST, "management/**").hasAnyAuthority(ADMIN_WRITE.name(), MANAGER_READ.name())
                                .requestMatchers(PUT, "management/**").hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                .requestMatchers(DELETE, "management/**").hasAnyAuthority(ADMIN_WRITE.name(), MANAGER_DELETE.name())

                                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                                .requestMatchers("/api/v1/management/**").hasAnyRole(ADMIN.name(), MANAGER.name())

                                .requestMatchers(GET, "admin/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "admin/**").hasAuthority(ADMIN_WRITE.name())
                                .requestMatchers(PUT, "admin/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "admin/**").hasAuthority(ADMIN_DELETE.name())

                                .anyRequest()
                                .authenticated()
                )
                .userDetailsService(userDetailsServiceImpl)
                .exceptionHandling(e -> e.accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
