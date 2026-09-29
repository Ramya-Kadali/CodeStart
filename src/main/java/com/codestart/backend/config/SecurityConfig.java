package com.codestart.backend.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    
	@Bean
	public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	 @Bean
	    public SecurityFilterChain securityFilterChain(
	            HttpSecurity http) throws Exception {

	        
			http
	            // Disable CSRF for our JWT-based API
	            .csrf(csrf -> csrf.disable())

	            // JWT authentication is stateless
	            .sessionManagement(session ->
	                    session.sessionCreationPolicy(
	                            SessionCreationPolicy.STATELESS
	                    )
	            )

	            // Which URLs require authentication
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers(
	                            "/api/users/login",
	                            "/api/users/register"
	                    ).permitAll()
	                    .anyRequest().authenticated()
	            )

	            // Run our JWT filter before Spring's authentication filter
	            .addFilterBefore(
	                    jwtAuthenticationFilter,
	                    UsernamePasswordAuthenticationFilter.class
	            );

	        return http.build();
	    }
}
