package cn.itcast.shiro;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import cn.itcast.shiro.realm.MonitorRealm;

@SpringBootApplication
public class ShiroApplication {
	/*
	 *  <!-- 配置Shiro过滤器,先让Shiro过滤系统接收到的请求 -->
    	<!-- 这里filter-name必须对应applicationContext.xml中定义的<bean id="shiroFilter"/> -->
    	<!-- 使用[/*]匹配所有请求,保证所有的可控请求都经过Shiro的过滤 -->
    	<!-- 通常会将此filter-mapping放置到最前面(即其他filter-mapping前面),以保证它是过滤器链中第一个起作用的 -->
	 */
	@Bean
	public FilterRegistrationBean shiroFilterRegistrationBean() {
		// 该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理 
		Map<String, String> initParameters = new HashMap<String, String>() ;
		initParameters.put("targetFilterLifecycle", "true") ;
		
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean() ;
		filterRegistrationBean.setName("shiroFilter") ;
		filterRegistrationBean.setFilter(new DelegatingFilterProxy());
		filterRegistrationBean.setInitParameters(initParameters);
		filterRegistrationBean.setOrder(0);
		
		Collection<String> urlPatterns = new ArrayList<String>() ;
		urlPatterns.add("/*");
		
		filterRegistrationBean.setUrlPatterns(urlPatterns);
		System.out.println("=========================Shiro 拦截器启用 ====================");
		System.out.println(filterRegistrationBean.getInitParameters());
		System.out.println(filterRegistrationBean.getUrlPatterns());
		System.out.println(filterRegistrationBean.getServletNames());
		System.out.println(filterRegistrationBean.getServletRegistrationBeans());
		System.out.println(filterRegistrationBean.getOrder());
		return filterRegistrationBean ;
	}
	
	// **************************** Shiro 配置 *********************************
	// 配置ehcache
	@Bean
	public EhCacheManager getEhCacheManager() {
		return new EhCacheManager() ;
	}
	
	// 保证实现了Shiro内部lifecycle函数的bean执行
	@Bean(name="lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor() ;
	}
	
	// 添加securityManager定义 
	@Bean
	public DefaultWebSecurityManager getDefaultWebSecurityManager(MonitorRealm monitorRealm) {
		// 设置自定义realm 
		return new DefaultWebSecurityManager(monitorRealm) ;
	}
	
	/*@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean(DefaultWebSecurityManager securityManager) {
		MethodInvokingFactoryBean invokingFactoryBean = new MethodInvokingFactoryBean() ;
		invokingFactoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		invokingFactoryBean.setArguments(new Object[]{securityManager});
		return invokingFactoryBean ;
	}*/
	
	@Bean(name="shiroFilter")
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
		System.out.println("================ 进入shiro拦截器 ================");
		// <!--anno 不拦截-->
        // <!--authc 拦截-->
		Map<String, String> filterMap = new HashMap<String, String>() ;
		filterMap.put("/login.jsp", "anon") ;
		filterMap.put("/login.do", "anon") ;
		filterMap.put("/index.jsp", "anon") ;
		filterMap.put("/index.do", "anon") ;
		filterMap.put("/error.jsp", "anon") ;
		filterMap.put("/*.jsp", "authc") ;
		filterMap.put("/shiro/demo/**", "authc") ;
		
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean() ;
		shiroFilter.setSecurityManager(securityManager);
		shiroFilter.setLoginUrl("/500.jsp");
		shiroFilter.setSuccessUrl("/500.jsp");
		shiroFilter.setUnauthorizedUrl("/500.jsp");
		shiroFilter.setFilterChainDefinitionMap(filterMap);
		return shiroFilter ;
	}
	
	// 如果使用Shiro相关的注解，需要在springmvc-servlet.xml中配置一下信息
	@Bean
	@DependsOn(value="lifecycleBeanPostProcessor")
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator() ;
		return advisorAutoProxyCreator ;
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor() ;
		advisor.setSecurityManager(securityManager);
		return advisor ; 
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ShiroApplication.class, args) ;
	}
}
