package com.funshion.artemis.autotest.web;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;
import org.eclipse.birt.core.exception.BirtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.funshion.artemis.autotest.service.DataPrepareManager;
import com.funshion.artemis.common.util.Tools;

/**
 * 自动化测试接口
 * 
 * @author shenwf Reviewed by
 */
@Controller
public class AutoTestInterfaceAction {
	private static final Logger log = LoggerFactory.getLogger(AutoTestInterfaceAction.class);
	@Autowired
	private DataPrepareManager dataPrepareManager;

	/**
	 * 获取报告数据(以文件形式返回)
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/report/getdata")
	public ModelAndView getData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String csvFile = dataPrepareManager.getReportData(request, response);
		ModelAndView mv = new ModelAndView("redirect:/temp/" + csvFile);
		return mv;
	}

	/**
	 * 获取报告数据(以字符串形式返回)
	 * 
	 * @param request
	 * @param response
	 * @throws BirtException
	 * @throws DocumentException
	 * @throws IOException
	 */
	@RequestMapping("/report/get-string-data")
	public void getStringData(HttpServletRequest request, HttpServletResponse response) throws BirtException, DocumentException, IOException {
		String data = dataPrepareManager.getReportStringData(request, response);
		Tools.transmitDataToJsp(request, response, data);
	}
	
	/**
	 * 获取报告数据(以字符串形式返回)
	 * 
	 * @param request
	 * @param response
	 * @throws BirtException
	 * @throws DocumentException
	 * @throws IOException
	 */
	@RequestMapping("/report/get-string-data2")
	public void getStringData2(HttpServletRequest request, HttpServletResponse response) throws BirtException, DocumentException, IOException {
		String data = dataPrepareManager.getReportStringData2(request, response);
		Tools.transmitDataToJsp(request, response, data);
	}
}
