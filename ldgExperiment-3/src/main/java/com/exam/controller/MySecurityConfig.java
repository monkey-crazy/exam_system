package com.exam.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.exam.service.StudentService;
import com.exam.service.TeacherService;

@EnableWebSecurity
public class MySecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private StudentService studentService;
	@Autowired
	
	private TeacherService teacherService;
	@Autowired
	private MyAuthenticationController mac;
	@Autowired
	PasswordEncoder pe;
	Log log = LogFactory.getLog(MySecurityConfig.class);

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/regix", "/login").permitAll()// ���ж����Է��ʡ�index��,'/regix'
				// .antMatchers("/user","/student","/admin").authenticated();//
				// ��/user������/student������admin����ͷ���������֤
				.antMatchers("/student/**").hasAuthority("STUDENT").antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/teacher/**").hasAuthority("TEACHER");
		http.formLogin().loginPage("/login");// ʹ��Ĭ�ϵĵ�½ҳ��
		http.formLogin().successForwardUrl("/index");
		// ֻ����һ��

		// .successHandler(new AuthenticationSuccessHandler() {
//			
//			@Override
//			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//					Authentication au) throws IOException, ServletException {
//				// TODO Auto-generated method stub
//				if(au.getPrincipal() instanceof Student) {
//					Student stu =(Student)au.getPrincipal();
//					request.getSession().setAttribute("student", stu);
//				}
//			}
//		});//�û��Լ��ĵ�½ҳ��(��Ҫһ��get����������ҳ��)
		// http.logout().logoutSuccessUrl("/login");//ע��֮����ת���Ǹ�ҳ�棻/logout
		 //http.rememberMe();//��ס����
		 //http.rememberMe().rememberMeParameter("remember");//���˶���
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		// �ڴ��û���Ȩ
		auth.inMemoryAuthentication().withUser("admin1").password(pe.encode("123456")).roles("ADMIN").and()
				.withUser("admin2").password(pe.encode("123456")).roles("ADMIN");

		// ���ݿ���Ȩ
		
		log.info(auth.getDefaultUserDetailsService()+"��֤");
		auth.userDetailsService(mac).passwordEncoder(pe);
	}

}