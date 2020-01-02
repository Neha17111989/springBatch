package com.altimetrik;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//In Memory No H2
		/*
		 * auth.inMemoryAuthentication().withUser("neha").password("mehta").roles(
		 * "USER") .and().withUser("nitin").password("mehta").roles("ADMIN");
		 */
		
		//In Memory H2
		/*
		 * auth.jdbcAuthentication().dataSource(dataSource).withDefaultSchema().
		 * withUser( User.withUsername("neha") .password("mehta"). roles("ADMIN") )
		 * .withUser( User.withUsername("nitin") .password("mehta") .roles("ADMIN") );
		 */
		
		//In H2
		//auth.jdbcAuthentication().dataSource(dataSource);
		
		//H2 with getting custom table and Query based approch..
		auth.jdbcAuthentication().dataSource(dataSource).
			usersByUsernameQuery("select username,password,enabled from users where username =?")
		.authoritiesByUsernameQuery("select username,authority from authorities where username =?");
		
	}

	@Bean
	PasswordEncoder getEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*
		 * http.authorizeRequests().antMatchers("/","static/js").permitAll().
		 * antMatchers("/user").hasRole("ADMIN").and().formLogin();
		 */
		http.authorizeRequests().antMatchers("/user").hasRole("USER").antMatchers("/admin").hasRole("ADMIN")
				.antMatchers("/").permitAll().and().formLogin();
	}

}
