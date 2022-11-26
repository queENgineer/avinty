package com.avinty.hr.conf;

import com.avinty.hr.auth.JwtTokenFilter;
import com.avinty.hr.auth.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig implements WebMvcConfigurer {
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
			http.csrf().disable()
			.authorizeRequests().antMatchers("/API/V1/login").permitAll()
			.anyRequest().authenticated();
		
		
		http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	
	
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			registry.addMapping("/API/V1/**")
				.allowedOrigins("http://localhost:5000")
				.allowedMethods("*")
				.allowedHeaders("*")
				.allowCredentials(true).maxAge(-1);
		}
	
	@Autowired
	private JwtTokenFilter jwtTokenFilter;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	
	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
		AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(getBCryptPasswordEncoder());
		return authenticationManagerBuilder.build();
	}
	
	
	@Autowired
	public void configurePasswordEncoder(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(userDetailsService).passwordEncoder(getBCryptPasswordEncoder());
		
	}
	
}