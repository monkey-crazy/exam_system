package com.exam.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.exam.dao.StudentDao;
import com.exam.entity.Student;

@Service
public class StudentService  {
	@Autowired
	private StudentDao sd;
	Log log = LogFactory.getLog(StudentService.class);

//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		// TODO Auto-generated method stub
//		// return sd.findByStudentName(username);
//		log.info("��½"+username);
//		Student student = sd.findById(username).get();
//		// ȥ�ڴ�����
//		if (student == null) {
//			InMemoryUserDetailsManager iud = new InMemoryUserDetailsManager();
//log.info(iud.loadUserByUsername(username)+"Ȩ��");
//			return iud.loadUserByUsername(username);
//		}
//		return student;
//
//	}

	public Student findByIdName(String id, String name) {
		return sd.findByStudentIdAndStudentName(id, name);
	}
	//ͨ��id��ѯ
	public Student 	quearyStudentById(String id) {
		log.info("ͨ��id��ѯѧ����Ϣ��id="+id);
		return sd.findById(id).get();
		
	}
//	//ͨ��������ѯѧ����Ϣ
	public List<Student> quearyStudentByName(String name){
		log.info("ͨ��������ѯѧ����Ϣ ,name="+name);
		return sd.findByStudentNameLike("%"+name+"%");
	}
	
	//��ѯ����ѧ��
	public List<Student> queryStudentAll(){
		log.info("��ѯ����ѧ��");
		return sd.findAll();
	}
	//ɾ��ѧ��
	public boolean deletStudent(String id) {
		log.info("ɾ��ѧ�� id="+id);
		
		try {
			sd.deleteById(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	//ѧ����Ϣ��ʽ��
	public void format(Page<Student> pae,Model model) {
		
		log.info("0++");
		log.info(pae.getNumber());
		log.info(pae.getContent().size() + "����");
		log.info(pae.getSize() + "ҳ��С1");

		List<Map<String, Object>> stuss = new ArrayList<Map<String, Object>>();// �ü��ϴ洢ѧ����map
		List<Map<String, Object>> st = new ArrayList<Map<String, Object>>();// �ü��ϴ洢ѧ����map
		Map<String, Object> Maps = new HashMap<String, Object>();
		for (int i = 0; i < pae.getContent().size(); i++) {// �������ѧ������lists
			log.info(pae.getSize() + "ҳ��С");
			log.info(pae.getContent().get(i));
			log.info(pae.getContent().get(i).getStudentName() + "��ҳ");
			Map<String, Object> stuMap = new HashMap<String, Object>();// ����һ��map���������洢һ��ѧ������Ϣ
			stuMap.put("studentId", pae.getContent().get(i).getStudentId());
			stuMap.put("studentName", pae.getContent().get(i).getStudentName());
			stuMap.put("major", pae.getContent().get(i).getMajor());
			// byte[] stu1 =lists.get(i).getPhoto() ;// ���ֽ�������ܴ����ݿ������ͼƬ���ֽ���
			String bs64 = Base64.getEncoder().encodeToString(pae.getContent().get(i).getPhoto());// ��Base64���ֽ�ת��ΪString
			log.info(bs64);
			stuMap.put("stuphoto", bs64);

			log.info(pae.getNumber());
			log.info(pae.getTotalPages() + "��");

			stuss.add(stuMap);// ������õ�ѧ��Map������ӵ�����
			log.info(i);
		}
		Maps.put("number", pae.getNumber());
		Maps.put("size", pae.getSize());
		Maps.put("totalPages", pae.getTotalPages());
		st.add(Maps);
		model.addAttribute("students", stuss);
		model.addAttribute("exam", st);
		
	}
	//��ҳ��ʾ
	public Page<Student> showPage( int page ,int size){
		int sum=(int)sd.count();
		log.info(sum+"�ܼ�¼");
		return (sd.findAll(PageRequest.of(page, size)));
	}
	//����/�޸�ѧ��
	public Student save(Student student) {
		return sd.save(student);
	}
	
	//����רҵ
	public List<String> findmajor(){
		return sd.findMajor();
	}
	//�鿴����ѧ��
	public List<Student> findall(){
		return sd.findAll();	}
}
