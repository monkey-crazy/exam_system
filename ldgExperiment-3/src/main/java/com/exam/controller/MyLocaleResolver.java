package com.exam.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;



public class MyLocaleResolver implements LocaleResolver {

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
//		// TODO Auto-generated method stub
		Locale lo = Locale.getDefault();
//		String lang = "zh";
//		if (request.getParameter("l") != null) {
//			lang = request.getParameter("l");
//		}
//
//		String coun = "CN";
//		if (request.getParameter("c") != null) {
//			coun = request.getParameter("c");
//		}

//		lo = new Locale(lang, coun);
		//¹ú¼Ê»¯
		String lang = request.getParameter("l");
		String coun = request.getParameter("c");
		if(!StringUtils.isEmpty(lang)&&!StringUtils.isEmpty(coun)) {
			lo=new Locale(lang,coun);
			request.getSession().setAttribute("Locale", lo);
		}
		if(request.getSession().getAttribute("Locale")!=null) {
			lo=(Locale)request.getSession().getAttribute("Locale");
		}
		return lo;
	}

	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub

	}

}
