package com.funshion.artemis.common.interceptors;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.funshion.artemis.common.util.TimeUtils;

/**
 * artemis 拦截器
 * 
 * @author shenwf Reviewed by
 */
public class SystemInterceptors implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
		if(auth != null) {
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				String username = ((UserDetails) principal).getUsername();
				String url = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
				System.out.println(TimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") +"|" + request.getRemoteHost() + "|username:" + username + "|" + url);
			} else {
				String username = "none";
				String url = request.getRequestURI() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
				System.out.println(TimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") +"|" + request.getRemoteHost() + "|username:" + username + "|" + url);
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
