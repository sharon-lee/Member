package com.webapp.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.command.LoginCommand;
import com.webapp.exception.IdPasswordNotMatchException;
import com.webapp.model.AuthInfo;
import com.webapp.service.AuthService;

@Controller
@RequestMapping("/login") /*RequestHandlerMapping이 List up*/
public class LoginController {
	
	static Log log = LogFactory.getLog(LoginController.class);
	//Injection - 
	@Autowired
	AuthService service;
	/*
	@ModelAttribute("login")
	LoginCommand getCommand() {
		LoginCommand command = new LoginCommand();
		command.setRememberID(true);		
		
		return command;
	}
	*/
	@RequestMapping(method=RequestMethod.GET)
	public String loginForm(@ModelAttribute("login") LoginCommand command) {
		log.info("loginForm()...");
		command.setRememberID(true);
		
		return "login/loginForm";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String login(@ModelAttribute("login") LoginCommand login, Errors errors, HttpSession session) {
		log.info("login()..." + login);
		/*
		 * Validation - email, password
		 */
		
		if (errors.hasErrors()) { //Field Error
			errors.reject("idPasswordNotMatch");
			return "login/loginForm";
		}
		
		/*
		 * Login process
		 * DB조회 실패하면 Exception try~catch(), 성공이면 session에 AuthInfo 전달
		 */
		try {
			AuthInfo auth = service.authenticate(login.getEmail(), login.getPassword());
			session.setAttribute("auth", auth); //Login Success
			
		} catch(IdPasswordNotMatchException ex) { //DB Error
			errors.reject("idPasswordNotMatch");
			return "login/loginForm";
		}
		return "redirect:/"; /*redirect to Home*/
	}
	
}
