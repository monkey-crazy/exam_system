package com.example.demo;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.LocaleResolver;

import com.exam.controller.ExamConfig;
import com.exam.controller.MyLocaleResolver;

@SpringBootApplication
@EntityScan("com.exam.entity")
@ComponentScan("com.exam.controller")
@Import(ExamConfig.class)
public class LdgExperiment3Application {

	public static void main(String[] args) {
		SpringApplication.run(LdgExperiment3Application.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}
	
	
	
	
	public class IsStu {
	    private Boolean isstu;
	}
		
	

	 @Bean
	public IsStu isstu(){
	    return new IsStu();
	}

}
