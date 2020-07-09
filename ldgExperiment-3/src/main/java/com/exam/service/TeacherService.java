package com.exam.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;

import com.exam.dao.TeacherDao;
import com.exam.entity.Question;
import com.exam.entity.Student;
import com.exam.entity.Teacher;


@Service
public class TeacherService implements UserDetailsService {

	@Autowired
	private TeacherDao td;

	Log log = LogFactory.getLog(TeacherService.class);
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
//		return td.getOne(username);
		Teacher teacher = td.findById(username).get();
		// 去内存中找
		if (teacher == null) {
			InMemoryUserDetailsManager iud = new InMemoryUserDetailsManager();
			return iud.loadUserByUsername(username);
		}
		return teacher;
	}
//添加老师
	public Teacher addTeacher(Teacher t) {
		t.getPassword();
		
		log.info(t.getMajor()+"老师");
		log.info(t.getTeacherId());
		log.info("老师的权限"+t.getPower().getPowerId());
		log.info("老师的角色"+t.getRoles().get(0).getRoleId());
		return td.save(t);
	}
	//分页查看老师信息
	public Page<Teacher> showPage(int page,int size){
		return (td.findAll(PageRequest.of(page, size)));
	}
	//通过老师id查找
	public Teacher findById(String id ) {
		log.info("查找的老师id="+id);
		return td.findById(id).get();
	}
	//通过老师id删除老师
	public boolean deletet(String id) {
		log.info("删除胡老师是："+id);
		try {
			td.deleteById(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	//查找所有老师
	public List<Teacher> findall(){
		return td.findAll();
	}
}
