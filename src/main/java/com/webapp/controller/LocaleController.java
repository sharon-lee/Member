package com.webapp.controller;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

@Controller
public class LocaleController {
	
	@Autowired
	LocaleResolver localeResolver;
	
	//HandlerAdapter가 response에서 PrintWriter에 넣어줌
	@RequestMapping("/locale/{language:[a-z]{2}}")
	public void setLocale(HttpServletRequest request, HttpServletResponse response, PrintWriter out, @PathVariable String language) {
		
		Locale locale = new Locale(language);
		
		localeResolver.setLocale(request, response, locale );
		
		out.println("locale changed... " + locale.getLanguage());
	}
}


