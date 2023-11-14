package com.fastcampus.toyproject.config.security.config;

import com.fastcampus.toyproject.config.security.jwt.JwtAccessDeniedHanlder;
import com.fastcampus.toyproject.config.security.jwt.JwtAuthenticationEntryPoint;
import com.fastcampus.toyproject.config.security.jwt.TokenProvider;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] auth = {
        "/auth", "/auth/**"
    };
    private static final String[] getTrip = {
        "/api/trip", "/api/trip/**"
    };
    private static final String[] error = {
        "/error"
    };

    private final TokenProvider tokenProvider;
    private final CorsFilter corsFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHanlder jwtAccessDeniedHanlder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf().disable()
            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHanlder)
            .and()

            .headers()
            .frameOptions()
            .sameOrigin()
            .and()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeHttpRequests()
            .requestMatchers(
                Arrays.stream(auth)
                    .map(AntPathRequestMatcher::new)
                    .toArray(AntPathRequestMatcher[]::new)
            ).permitAll()
            .requestMatchers(
                Arrays.stream(error)
                    .map(AntPathRequestMatcher::new)
                    .toArray(AntPathRequestMatcher[]::new)
            ).permitAll()
//            .requestMatchers(
//                Arrays.stream(getTrip)
//                    .map(s -> new AntPathRequestMatcher(s, "GET"))
//                    .toArray(AntPathRequestMatcher[]::new)
//            ).permitAll()
            .anyRequest().authenticated()
            .and()

            .apply(new JwtSecurityConfig(tokenProvider));

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
