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
		http.authorizeRequests().antMatchers("/regix", "/login").permitAll()// 所有都可以访问‘index’,'/regix'
				// .antMatchers("/user","/student","/admin").authenticated();//
				// ‘/user’。‘/student’，‘admin’开头的请求会验证
				.antMatchers("/student/**").hasAuthority("STUDENT").antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/teacher/**").hasAuthority("TEACHER");
		http.formLogin().loginPage("/login");// 使用默认的登陆页面
		http.formLogin().successForwardUrl("/index");
		// 只能用一个

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
//		});//用户自己的登陆页面(需要一个get请求来返回页面)
		// http.logout().logoutSuccessUrl("/login");//注销之后跳转到那个页面；/logout
		 //http.rememberMe();//记住密码
		 //http.rememberMe().rememberMeParameter("remember");//个人定制
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		// 内存用户授权
		auth.inMemoryAuthentication().withUser("admin1").password(pe.encode("123456")).roles("ADMIN").and()
				.withUser("admin2").password(pe.encode("123456")).roles("ADMIN");

		// 数据库授权
		
		log.info(auth.getDefaultUserDetailsService()+"验证");
		auth.userDetailsService(mac).passwordEncoder(pe);
	}

}