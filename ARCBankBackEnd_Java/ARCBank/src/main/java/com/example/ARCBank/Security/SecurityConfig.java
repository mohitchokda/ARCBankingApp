package com.example.ARCBank.Security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.ARCBank.Jwt.JwtAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	@Autowired
//	UserNamePwdAuthentication userAuth;
	
	//UserDetailsService userDetailsService;

	  //private JwtAuthenticationEntryPoint authenticationEntryPoint;

	  private JwtAuthenticationFilter jwtAuthenticationFilter;
	  //private JwtAuthEntryPoint jwtEntryPoint;
	  
	  public SecurityConfig(//JwtAuthEntryPoint jwtEntry
	                          JwtAuthenticationFilter authenticationFilter){
	        //this.authenticationEntryPoint = authenticationEntryPoint;
		  	//this.jwtEntryPoint = jwtEntry;
	        this.jwtAuthenticationFilter = authenticationFilter;
	  }
	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//	        return configuration.getAuthenticationManager();
//	}
	
	@Bean
	public SecurityFilterChain handleSecurity(HttpSecurity http) throws Exception {
		
		http.csrf(csrf -> csrf.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.authorizeHttpRequests(req ->{ 
			req.requestMatchers("/user/register").permitAll();
			req.requestMatchers("/user/login").permitAll();
			
			//Very Imp
			req.anyRequest().authenticated();
		})
		.httpBasic(Customizer.withDefaults())
		.formLogin(login -> login.permitAll())
		//.exceptionHandling(exp -> exp.authenticationEntryPoint(jwtEntryPoint))
		.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean 
	public PasswordEncoder pwdEncode() {
		return new BCryptPasswordEncoder();
	}	
	
	//CORS Handler 
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    configuration.setAllowedOrigins(Arrays.asList("*"));
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("*"));
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
	
	
	
//	//In Memory Authentication for custom user details along with roles
//	@Bean
//	public UserDetailsService usersDetails() {
//		UserDetails user = User.withUsername("user")
//			.password(pwdEncode().encode("pwd"))
//			.build();
//			
//		return new InMemoryUserDetailsManager(user);
//	}
	
}
