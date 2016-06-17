package cn.itcast.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import cn.itcast.security.auth.LoginSuccessHandler;
import cn.itcast.security.service.CustomUserDetailsService;

/*
 * 配置Spring Security
 */
@Configuration
@EnableWebSecurity
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
			.and()
			.logout()
			// 退出登录后的默认网址是”/home”
			.logoutSuccessUrl("/home")
			.permitAll()
			.invalidateHttpSession(true)
			.and()
			// 登录后记住用户，下次自动登录
	        // 数据库中必须存在名为persistent_logins的表
	        // 建表语句见
			.rememberMe()
			.tokenValiditySeconds(1209600)
			// 指定记住登录信息所使用的数据源
			.tokenRepository(tokenRepository()) ;
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {	
		// 指定密码加密所使用的加密器为passwordEncoder()
		// 需要将密码加密后写入数据库 
		auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
		//不删除凭据，以便记住用户
		auth.eraseCredentials(false);		
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(4);
	}

	@Bean
	public JdbcTokenRepositoryImpl tokenRepository(){		
		JdbcTokenRepositoryImpl j = new JdbcTokenRepositoryImpl();
		j.setDataSource(dataSource1);
		return j;
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandler(){
		return new LoginSuccessHandler();
	}
}
