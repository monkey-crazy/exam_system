package com.exam.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@ComponentScan({ "com.exam.service", "com.exam.controller" })
@EnableJpaRepositories("com.exam.dao")
public class ExamConfig {
	//加密器（返回一个）
	@Bean
	public PasswordEncoder PasswordEncoder() {

		return new BCryptPasswordEncoder();
	}
}
