package com.funshion.artemis.qunit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QunitWeb {
	@RequestMapping("/test")
	public ModelAndView test() {
		String message = "";
		ModelAndView mv = new ModelAndView("test/test", "message", message);
		return mv;
	}
}
