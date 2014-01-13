package com.funshion.artemis.log.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.funshion.artemis.custom.service.AdReportSqlManager;
import com.funshion.artemis.log.dao.OperationDao;

@Service
public class OperationManager {
	@Autowired
	private OperationDao operationDao;
	
	public void saveObj(String url) {
		operationDao.save(url);
	}

	public void ctxSaveObj(HttpServletRequest request, String url) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
		OperationManager operationManager = (OperationManager) ctx.getBean("operationManager");
		operationManager.saveObj(url);
	}
}
