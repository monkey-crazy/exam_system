package com.exam.controller;

import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;

import com.exam.dao.StudentDao;
import com.exam.dao.TeacherDao;
import com.exam.entity.Student;
import com.exam.entity.Teacher;

@Controller
public class MyAuthenticationController implements UserDetailsService {

	@Autowired
	private TeacherDao td;
	@Autowired
	private StudentDao sd;
org.apache.commons.logging.Log log =LogFactory.getLog(MyAuthenticationController.class);
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub

		try {
			// student������
			log.info(username+"--1");
			Student student = sd.findById(username).get();
			log.info(student.getStudentId()+"--2");
//			// ѧ��������
//			if (student == null) {
//				Teacher teacher = td.findById(username).get();
//				log.info(teacher.getTeacherId()+"--3");
				if (student == null) {
					// ȥ�ڴ�����
					InMemoryUserDetailsManager iud = new InMemoryUserDetailsManager();
					return iud.loadUserByUsername(username);
				}
//				return teacher;
//			}
		return student;

		} catch (Exception e) {
			// TODO: handle exception
			//teacher������֤
			Teacher teacher = td.findById(username).get();
			log.info(teacher.getTeacherId()+"--3");
			if (teacher == null) {
				// ȥ�ڴ�����
				InMemoryUserDetailsManager iud = new InMemoryUserDetailsManager();
				return iud.loadUserByUsername(username);
			}
			return teacher;
			
		}
	}

}
