package com.evstation.charge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import com.evstation.charge.config.auth.CustomOAuth2UserService;
import com.evstation.charge.config.auth.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()
			
			.authorizeHttpRequests()
			.antMatchers("/", "/css/**", "/img/**", "/js/**").permitAll()
            .antMatchers("/api/v1/**").hasRole(Role.USER.name())
            .anyRequest().authenticated()
        .and()
            .logout()
                .logoutSuccessUrl("/")
        .and()
            .oauth2Login()
                .userInfoEndpoint()
            .userService(customOAuth2UserService);
		
		
		
		
		
		return http.build();
	}
}
