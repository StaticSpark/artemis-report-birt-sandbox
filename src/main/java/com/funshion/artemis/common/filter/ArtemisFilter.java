package com.funshion.artemis.common.filter;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.funshion.artemis.common.util.TimeUtils;
import com.funshion.artemis.custom.web.AdReportAction;
import com.funshion.artemis.log.service.OperationManager;

public class ArtemisFilter implements Filter{
	private static final Logger log = LoggerFactory.getLogger(AdReportAction.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		Authentication auth =  SecurityContextHolder.getContext().getAuthentication();
//		if(auth != null) {
//			Object principal = auth.getPrincipal();
			String param = (httpRequest.getQueryString() == null ? "" : "/" + httpRequest.getQueryString());
			String url = httpRequest.getRequestURI() + param;
			String username = "";
//			if (principal instanceof UserDetails) {
//				username = ((UserDetails) principal).getUsername();
//			} else {
//				username = "none";
//			}
//			OperationManager operationManager = new OperationManager();
//			long start = System.currentTimeMillis();
//			if(url.contains("frameset")) {
//				try {
//					operationManager.ctxSaveObj(httpRequest, url);
//				} catch(Exception e) {
//				}
//				
//				long end = System.currentTimeMillis();
//				System.out.println("耗时:" + (end - start));
			if(!url.endsWith("js") && !url.endsWith("css")&& !url.endsWith("gif")&& !url.endsWith("png")&& !url.endsWith("jpg")) {
				System.out.println(TimeUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss") +"|" + request.getRemoteHost() + "|username:" + username + "|" + url);
			}
				
//			}
//		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
