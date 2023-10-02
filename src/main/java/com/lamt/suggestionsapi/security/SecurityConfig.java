package com.lamt.suggestionsapi.security;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

import com.lamt.suggestionsapi.security.filter.AuthenticationFilter;
import com.lamt.suggestionsapi.security.filter.ExceptionHandlingFilter;
import com.lamt.suggestionsapi.security.filter.JWTAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secret}")
    private String secret;

    AuthenticationManager authManager;
    CorsConfigurationSource corsConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authFilter = new AuthenticationFilter(authManager, secret);
        authFilter.setFilterProcessesUrl("/auth");
        http.headers(headers -> headers.frameOptions().disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests()
                .requestMatchers(toH2Console())
                .permitAll()
                .requestMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .cors(cors -> cors.configurationSource(corsConfigurationSource))
                .addFilterBefore(new ExceptionHandlingFilter(), AuthenticationFilter.class)
                .addFilter(authFilter)
                .addFilterAfter(new JWTAuthorizationFilter(secret), AuthenticationFilter.class)
                .logout(logout -> logout.logoutUrl("/logout"))
                .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
