package com.exam.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcController implements WebMvcConfigurer {

	/*
	 * ��������ӳ��
	 */
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addViewController("/regix").setViewName("student/regix");
		registry.addViewController("/index").setViewName("/index");
	//	registry.addViewController("/admin/markPaper").setViewName("admin/markingPaper");
		
		
	}

	/*
	 * ��ӱ��ص���Դ·��
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler("/image/**").addResourceLocations("file:D:/DeskTop/photo/");
	}
	

}
