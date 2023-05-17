package com.brayant.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.brayant.demo.service.UserService;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
/*
	private UserService userService;

	public SecurityConfiguration(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@Bean
	public static BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(this.userService);
		auth.setPasswordEncoder(bCryptPasswordEncoder());
		return auth;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
	    http.authorizeRequests()
		//configuration des url valide pour sauthentifier
		.antMatchers("/","/home","/search","/recette/**","recettes/**","/description","/recetteCategorie","/recherche","/login","/register").permitAll()
		.antMatchers("/admin**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/static/**","/static/bootstrap-5.0.2-dist/**","resources/static/**").permitAll()
		.anyRequest().permitAll()
		.and()
		// formulaire de connexion
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.failureUrl("/login?error=true")
		.defaultSuccessUrl("/adminrecettes", true)
		.permitAll()
		.usernameParameter("email")
		.passwordParameter("password")
		.and()
		//gestion de la deconnexion
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/home")
		.and()
		// gestion des exeptions
		.exceptionHandling()
		.accessDeniedPage("/home");
	}
	*/
	private UserService userService;

	public SecurityConfiguration(UserService userService) {
		super();
		this.userService = userService;
	}

	@Bean
	public static BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(this.userService);
		auth.setPasswordEncoder(bCryptPasswordEncoder());
		return auth;
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception{
		http
		.authorizeRequests().antMatchers("/static/**").permitAll()
		.antMatchers("/","/home","/search","/recette/**","recettes/**","/description","/recetteCategorie","/recherche","/login","/register").permitAll()
		.antMatchers("/admin**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/adminrecettes/**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/admincategories/**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/adminrecettes**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/admincategories**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/admincategorie/**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/admincategorie**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/adminrecette/**").hasAnyAuthority("ROLE_USER")
		.antMatchers("/adminrecette**").hasAnyAuthority("ROLE_USER")
		.anyRequest().permitAll()
		.and()
		.formLogin()
		.loginPage("/login")
		.permitAll()
		.defaultSuccessUrl("/adminrecettes", true)
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout")
		.permitAll();
	}
}
