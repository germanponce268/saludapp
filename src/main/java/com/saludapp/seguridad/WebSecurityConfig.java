package com.saludapp.seguridad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.saludapp.constantes.SecurityConstant;
import com.saludapp.seguridad.jwt.JwtAuthenticationEntryPoint;
import com.saludapp.seguridad.jwt.JwtRequestFilter;
import com.saludapp.servicios.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	JwtAuthenticationEntryPoint JwtAuthEntryPoint;
	
	@Autowired
	UserDetailsServiceImpl JpaUserDS;

	@Autowired
	public JwtRequestFilter JwtRequestFilter;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class).build();
	}

	@Bean
	public JWT jwt() {
		return new JWT();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.cors()
			.and()
			.csrf()
			.disable()
			.authorizeHttpRequests()
			.antMatchers(SecurityConstant.PUBLIC_URLS)
			.authenticated()
			.and()
				.authorizeHttpRequests()
				.antMatchers("/usuarios/registrar")
				.permitAll()
				.and()
			.exceptionHandling()
			.authenticationEntryPoint(JwtAuthEntryPoint)
			.and()
			.addFilterBefore(JwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
			.userDetailsService(JpaUserDS);
		return http.build();
	}
}
