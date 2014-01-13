package com.funshion.artemis.exportUtil.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funshion.artemis.exportUtil.service.McExportManager;

/**
 * 
 * @author guanzx
 *
 */
@Controller
public class McExportActionWeb {

	@Autowired
	private McExportManager mcExportManager;
	
	/**
	 * 播控imp一键导出功能
	 * web端需要传递所需的参数：播控id,时间mon_date,地域id,path,isVisualArea
	 * @throws IOException 
	 */
	@RequestMapping("/mc/one-key-export")
	public void oneKeyExport(HttpServletRequest request,HttpServletResponse response) throws IOException {				
		mcExportManager.exportExcel(request,response);
	}
}
