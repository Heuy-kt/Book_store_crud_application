package com.prunnytest.bookstore.security.config;

import com.prunnytest.bookstore.security.exceptions.CustomAccessDeniedHandler;
import com.prunnytest.bookstore.security.filter.JWTAuthenticationFilter;
import com.prunnytest.bookstore.security.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import static com.prunnytest.bookstore.model.enums.Roles.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        req -> req
                                .requestMatchers("login/**", "register/**", "refresh_token/**")
                                .permitAll()
                                .requestMatchers("/basic/**").hasAnyAuthority(ADMIN.name(), BASIC.name(), PREMIUM.name(), MANAGER.name())
                                .requestMatchers("/premium/**").hasAnyAuthority(ADMIN.name(), MANAGER.name(), ADMIN.name())
                                .requestMatchers("/admin/**").hasAnyAuthority(ADMIN.name())
                                .requestMatchers("/manage/**").hasAnyAuthority(ADMIN.name(), MANAGER.name())
                                .requestMatchers()
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
