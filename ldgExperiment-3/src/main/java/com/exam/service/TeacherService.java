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
		// ȥ�ڴ�����
		if (teacher == null) {
			InMemoryUserDetailsManager iud = new InMemoryUserDetailsManager();
			return iud.loadUserByUsername(username);
		}
		return teacher;
	}
//�����ʦ
	public Teacher addTeacher(Teacher t) {
		t.getPassword();
		
		log.info(t.getMajor()+"��ʦ");
		log.info(t.getTeacherId());
		log.info("��ʦ��Ȩ��"+t.getPower().getPowerId());
		log.info("��ʦ�Ľ�ɫ"+t.getRoles().get(0).getRoleId());
		return td.save(t);
	}
	//��ҳ�鿴��ʦ��Ϣ
	public Page<Teacher> showPage(int page,int size){
		return (td.findAll(PageRequest.of(page, size)));
	}
	//ͨ����ʦid����
	public Teacher findById(String id ) {
		log.info("���ҵ���ʦid="+id);
		return td.findById(id).get();
	}
	//ͨ����ʦidɾ����ʦ
	public boolean deletet(String id) {
		log.info("ɾ������ʦ�ǣ�"+id);
		try {
			td.deleteById(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	//����������ʦ
	public List<Teacher> findall(){
		return td.findAll();
	}
}
