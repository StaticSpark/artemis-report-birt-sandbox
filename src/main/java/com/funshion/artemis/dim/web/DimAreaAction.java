package com.funshion.artemis.dim.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.funshion.artemis.common.util.Tools;
import com.funshion.artemis.dim.service.DimAreaManager;

/**
 * 地域定向类
 * @author guanzx
 *
 */
@Controller
public class DimAreaAction {
	@Autowired
	private DimAreaManager areaManager;

	/**
	 * 返回所有地域
	 * @throws IOException
	 */
	@RequestMapping("/dim-area")
	public void allarea(HttpServletRequest request, HttpServletResponse response) {
		String json = areaManager.getAllArea();
		Tools.transmitDataToJsp(request,response,json);
	}
}
