package cn.itcast.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

import cn.itcast.security.auth.LoginSuccessHandler;

/*
 * 配置Spring Security
 */
@Configuration
@EnableWebMvcSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired 
	@Qualifier("dataSource1")
	private DataSource dataSource1;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//允许所有用户访问”/”和”/home”
		http.authorizeRequests()
			.antMatchers("/", "home") 
			.permitAll()
			// 其他地址的访问均需验证权限
			.anyRequest()
			.authenticated()
			.and()
			.formLogin()
			//指定登录页是”/login”
			.loginPage("/login")
			.permitAll()
			//登录成功后可使用loginSuccessHandler()存储用户信息，可选。
			.successHandler(loginSuccessHandler())
			.
	}
	
	
	@Bean
	public LoginSuccessHandler loginSuccessHandler(){
		return new LoginSuccessHandler();
	}
}
