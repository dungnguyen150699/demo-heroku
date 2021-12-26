package com.example.demo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;



@Configuration
@EnableWebSecurity
public class ConfigSercurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		http.authorizeRequests()
			.antMatchers("/admin/createAdmin","/register","/login","/doRegister","/css/**","**/js/**","**/images/**","/resources/**", "/static/**").permitAll()
	        .antMatchers("/admin","/delete/**").hasAuthority("ADMIN")
	        .antMatchers("/","/shop/**","/cart/**").hasAnyAuthority("ADMIN","USER") // admin mới xóa được sản phẩm
	        .anyRequest().authenticated()
	        .and()
	        .formLogin()
	        .loginPage("/login")
	        .usernameParameter("username")
	        .passwordParameter("password")
            .defaultSuccessUrl("/dologin",true)
            .failureUrl("/login?error").permitAll()
	        .and()
	        .logout().logoutSuccessUrl("/login").permitAll()
	        .and()
	        .exceptionHandling().accessDeniedPage("/403");
	}
}
