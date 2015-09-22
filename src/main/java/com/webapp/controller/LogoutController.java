package com.webapp.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	static Log log = LogFactory.getLog(LogoutController.class);
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		log.info("logout success...");
		
//		Session을 무효화
		session.invalidate();
		
		return "redirect:/";
	}

}
