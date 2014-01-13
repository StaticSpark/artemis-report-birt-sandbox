package com.funshion.artemis.user.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.funshion.artemis.user.service.UserManager;

@Controller
public class UserWeb {
	@Autowired
	private UserManager userManager;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/user")
	public ModelAndView list() {
		ModelAndView mv = new ModelAndView("user/list", "message", "");
		return mv;
	}

	@RequestMapping("/userInfo")
	public void getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String[]> map = request.getParameterMap();
		String[] iDisplayStart = map.get("iDisplayStart");
		String[] iDisplayLength = map.get("iDisplayLength");
		String[] sEcho = map.get("sEcho");
		String start = iDisplayStart[0];
		String length = iDisplayLength[0];

		String message = userManager.changeToDataTable(start, length);
		message = "{\"sEcho\":" + sEcho[0] + ",\"iTotalRecords\":\" " + userManager.getLength() + "\",\"iTotalDisplayRecords\":\"" + userManager.getLength() + "\",\"aaData\":[" + message + "]}";
		try {
			request.setCharacterEncoding("utf8");
			response.setCharacterEncoding("utf8");
			response.getWriter().println(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}