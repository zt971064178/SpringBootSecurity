package cn.itcast.security.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/*
 * 可以在这里将用户登录信息存入数据库
 */
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		// 获得授权后可得到用户信息   可使用SUserService进行数据库操作
		
	}
}
