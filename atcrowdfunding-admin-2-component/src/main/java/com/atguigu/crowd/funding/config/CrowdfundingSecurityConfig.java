package com.atguigu.crowd.funding.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // 开启全局方法权限管理功能
public class CrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder builder) throws Exception {
		// builder.inMemoryAuthentication().withUser("kathry").password("789789").roles("king");
		builder.userDetailsService(userDetailsService);
	}
	
	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.authorizeRequests().antMatchers("/index.html", "/bootstrap/**", "/css/**", "/fonts/**", "/img/**", "/jquery/**", "/layer/**", "/script/**", "/ztree/**").permitAll()
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/admin/to/login/page.html").permitAll().loginProcessingUrl("/admin/security/do/login.html").usernameParameter("loginAcct").passwordParameter("userPswd")
		.defaultSuccessUrl("/admin/to/main/page.html")
		.and().logout().logoutUrl("/admin/security/do/logout.html").logoutSuccessUrl("/index.html")
		.and().csrf().disable();
	}
}
