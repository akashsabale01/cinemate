package com.movieapp.cinemate.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsService userDetailsService;

	// User Creation
//	@Bean
//	public UserDetailsService userDetailsService(PasswordEncoder encoder) {
//
//		// InMemoryUserDetailsManager
//		UserDetails admin = User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build();
//
//		UserDetails user = User.withUsername("john").password(encoder.encode("john")).roles("USER").build();
//
//		return new InMemoryUserDetailsManager(admin, user);
//	}

	// Configuring HttpSecurity	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.csrf(customizer -> customizer.disable())
				.authorizeHttpRequests(request ->
						request.requestMatchers("/api/v1/user/login","/api/v1/user/register").permitAll()
								.anyRequest().authenticated()
						)
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.build();

//		return http.csrf().disable()// .cors().disable()
//				.authorizeHttpRequests()
//				.requestMatchers("/api/v1/user*/**", "/swagger-ui.html", "/v3/api-docs/**", "/swagger-ui.html",
//						"/swagger-ui/**")
//				.permitAll().and().authorizeHttpRequests().requestMatchers("/api/v1/admin*/**").authenticated()
//				.and().httpBasic()
//				// .and().formLogin()
//				.and().build();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder( new BCryptPasswordEncoder());
		provider.setUserDetailsService(userDetailsService);
		return provider;
	}

	// Password Encoding
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
