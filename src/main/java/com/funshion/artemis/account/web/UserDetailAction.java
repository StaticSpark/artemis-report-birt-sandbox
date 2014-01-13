package com.funshion.artemis.account.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funshion.artemis.common.util.Tools;

/**
 * 用户权限 web 类
 * 
 * @author shenwf Reviewed by
 */
@Controller
public class UserDetailAction {
	/**
	 * 判断用户是否登录
	 * @param request
	 * @param response
	 */
	@RequestMapping("/is-login")
	public void isLogin(HttpServletRequest request, HttpServletResponse response) {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		int isLogin = 1;
	    if(auth == null) {
	    	isLogin = 0;
	    }
	    Tools.transmitDataToJsp(request, response, isLogin + "");
	}
}
