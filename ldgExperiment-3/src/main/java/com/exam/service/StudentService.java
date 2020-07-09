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
//		log.info("登陆"+username);
//		Student student = sd.findById(username).get();
//		// 去内存中找
//		if (student == null) {
//			InMemoryUserDetailsManager iud = new InMemoryUserDetailsManager();
//log.info(iud.loadUserByUsername(username)+"权限");
//			return iud.loadUserByUsername(username);
//		}
//		return student;
//
//	}

	public Student findByIdName(String id, String name) {
		return sd.findByStudentIdAndStudentName(id, name);
	}
	//通过id查询
	public Student 	quearyStudentById(String id) {
		log.info("通过id查询学生信息，id="+id);
		return sd.findById(id).get();
		
	}
//	//通过姓名查询学生信息
	public List<Student> quearyStudentByName(String name){
		log.info("通过姓名查询学生信息 ,name="+name);
		return sd.findByStudentNameLike("%"+name+"%");
	}
	
	//查询所有学生
	public List<Student> queryStudentAll(){
		log.info("查询所有学生");
		return sd.findAll();
	}
	//删除学生
	public boolean deletStudent(String id) {
		log.info("删除学生 id="+id);
		
		try {
			sd.deleteById(id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	//学生信息格式化
	public void format(Page<Student> pae,Model model) {
		
		log.info("0++");
		log.info(pae.getNumber());
		log.info(pae.getContent().size() + "条数");
		log.info(pae.getSize() + "页大小1");

		List<Map<String, Object>> stuss = new ArrayList<Map<String, Object>>();// 用集合存储学生的map
		List<Map<String, Object>> st = new ArrayList<Map<String, Object>>();// 用集合存储学生的map
		Map<String, Object> Maps = new HashMap<String, Object>();
		for (int i = 0; i < pae.getContent().size(); i++) {// 逐个处理学生集合lists
			log.info(pae.getSize() + "页大小");
			log.info(pae.getContent().get(i));
			log.info(pae.getContent().get(i).getStudentName() + "分页");
			Map<String, Object> stuMap = new HashMap<String, Object>();// 创建一个map对象用来存储一个学生的信息
			stuMap.put("studentId", pae.getContent().get(i).getStudentId());
			stuMap.put("studentName", pae.getContent().get(i).getStudentName());
			stuMap.put("major", pae.getContent().get(i).getMajor());
			// byte[] stu1 =lists.get(i).getPhoto() ;// 用字节数组接受从数据库起初的图片的字节流
			String bs64 = Base64.getEncoder().encodeToString(pae.getContent().get(i).getPhoto());// 用Base64将字节转换为String
			log.info(bs64);
			stuMap.put("stuphoto", bs64);

			log.info(pae.getNumber());
			log.info(pae.getTotalPages() + "共");

			stuss.add(stuMap);// 将处理好的学生Map对象添加到集合
			log.info(i);
		}
		Maps.put("number", pae.getNumber());
		Maps.put("size", pae.getSize());
		Maps.put("totalPages", pae.getTotalPages());
		st.add(Maps);
		model.addAttribute("students", stuss);
		model.addAttribute("exam", st);
		
	}
	//分页显示
	public Page<Student> showPage( int page ,int size){
		int sum=(int)sd.count();
		log.info(sum+"总记录");
		return (sd.findAll(PageRequest.of(page, size)));
	}
	//保存/修改学生
	public Student save(Student student) {
		return sd.save(student);
	}
	
	//查找专业
	public List<String> findmajor(){
		return sd.findMajor();
	}
	//查看所有学生
	public List<Student> findall(){
		return sd.findAll();	}
}
