
package com.exam.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dao.PowerDao;
import com.exam.dao.RoleDao;
import com.exam.entity.Exam;
import com.exam.entity.Power;
import com.exam.entity.Role;
import com.exam.entity.Student;

import com.exam.entity.Teacher;
import com.exam.service.ExamService;
import com.exam.service.StudentExamService;
import com.exam.service.StudentService;
import com.exam.service.TeacherService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	private StudentExamService ses;

	@Autowired
	private PasswordEncoder pe;

	@Autowired
	private TeacherService ts;
	@Autowired
	private StudentService ss;
	@Autowired
	private ExamService es;
	@Autowired
	private RoleDao rd;
	@Autowired
	private PowerDao pd;
	
	
	Log log = LogFactory.getLog(AdminController.class);

	/*
	 * ѧ��
	 */
	// ��ѧ�Ų�ѯѧ��
	@RequestMapping("/findstuid")
	@ResponseBody
	public Student quearyStudentById(String studentid) {
		log.info("��ѯid");
		Student st=ss.quearyStudentById(studentid);
		String bs64 = Base64.getEncoder().encodeToString(st.getPhoto());
		//��ʱ�������ֶ�װͼƬ
		st.setPassword(bs64);
		return st;
	}

	// ��������ѯѧ����ģ����ѯ��
	@RequestMapping("/findstuname")
	@ResponseBody
	public List<Student> quearyStudentByName(String studentname, Model model) {
		
		List<Student> st = ss.quearyStudentByName(studentname);
		for(Student stu :st ) {
			String bs64 = Base64.getEncoder().encodeToString(stu.getPhoto());
			//��ʱ�������ֶ�װͼƬ
			stu.setPassword(bs64);	
		}
		return st;
	}

	// ��ʾ����ѧ����Ϣ
	@GetMapping("/findAll")
	public String quearyStudentAll(Model model, int page, int size) {
		// int size=3;//һҳ10����¼
		log.info("0++");
		Page<Student> pae = ss.showPage(page, size);

//	Student stus= pae.getContent().get(0);
		ss.format(pae, model);

		return "admin/student";
	}

	// ɾ��ѧ��
	@RequestMapping(value = "/deletestudent")
	@ResponseBody
	public String deletestudent(String stuid) {
			log.info(stuid+"ɾ����ѧ��id");
		
		if(ss.deletStudent(stuid)) {
			log.info("ɾ���ɹ���");
			return "ɾ���ɹ���";
			
		}else {
			log.info("fail");
			return "ɾ��ʧ�ܣ�";
		}
	}

	// �޸�ѧ����Ϣ
	@RequestMapping("/updatestu")
	@ResponseBody
	public Student updstudent(String stuid, String stuname)
			throws Exception {
		// ͨ��ѧ�Ų鵽ѧ����Ϣ
		Student st = ss.quearyStudentById(stuid);

		// ���޸ĵ���Ϣ�滻
		st.setStudentName(stuname);
		
		// Ȼ�����±��档
	return 	ss.save(st);
		

	}

//	//
//	/*
//	 * ��ʦ
//	 */
	//��ѯ��ʦ
	@RequestMapping("/findbyid")
	@ResponseBody
	public Teacher queteacher1( String teacherid){
		log.info(teacherid+"��ѯ");
		return ts.findById(teacherid);
	}
	// ��ʾ������ʦ��Ϣ
	@GetMapping("/showteacher")
	public String queteacherall(int page, int size, Model model) {

		Page<Teacher> pag = ts.showPage(page, size);
		log.info(pag);
		log.info(pag.getContent());
		if (pag.getContent().size() != 0) {
			log.info(pag.getContent().size() == 0);
			log.info(pag.getContent().get(0).getTeacherName());
			log.info(pag.getContent().get(0).getMajor());
		}

		model.addAttribute("teacher", pag);

		return "admin/teacher";

		// return page.getContent();

	}

	//�޸���ʦ��Ϣ
	@RequestMapping("/updateteacher")
	@ResponseBody
	public Teacher updteacher(String teacherId,String teacherName, String major) {
		Teacher te=ts.findById(teacherId);
		te.setTeacherName(teacherName);
		te.setMajor(major);
		log.info("�޸ĵ���ʦΪ��"+te.getTeacherName());
		ts.addTeacher(te);
		return te;
	}
	// ������ʦ
	@PostMapping("/addteacher")
	@ResponseBody
	public Teacher addteacher(Teacher teacher) {

		String passw = pe.encode(teacher.getPassword());
		teacher.setPassword(passw);
		

		
		// ����ע��Ľ�ɫadd(rd.getOne("student"))
		List<Role> rol =new ArrayList<Role>();

		log.info(rd.getOne("teacher").toString());
		rol.add(rd.getOne("teacher"));
		log.info(rol.toString());
		
		//����Ȩ��
		Power pow =pd.getOne("2");
		log.info(pow.getPowerName()+"��ʼ��Ȩ��");
		teacher.setRoles(rol);
		teacher.setPower(pow);
		
		
		log.info(teacher);
		log.info(teacher.getTeacherName());
		log.info(teacher.getTeacherId()+"888888");
		
		return ts.addTeacher(teacher);

	}
	//ɾ����ʦ
	@RequestMapping("/deleteteacher")
	@ResponseBody
	public String delteacher(String teacherid) {
		log.info(teacherid);
		
		if(ts.deletet(teacherid)) {
			log.info("ɾ���ɹ���");
			return "ɾ���ɹ���";
			
		}else {
			log.info("fail");
			return "ɾ��ʧ�ܣ�";
		}
	}
//	/*
//	 * �Ծ�
//	 */
	//�鿴���п���
@GetMapping("/showexam")
public String queexam(Model model , int page, int size){
	
	   Page<Exam> pag= es.showexam(page, size);
	   log.info(pag);
		log.info(pag.getContent());
		if (pag.getContent().size() != 0) {
			log.info(pag.getContent().size() == 0);
			log.info(pag.getContent().get(0).getExamId()+"___�Ծ�id");
			log.info(pag.getContent().get(0).getExamPla()+"___�ص� ");
		}

		model.addAttribute("exams", pag);
		return "admin/exam";
	}

   //���ҿ���
	@RequestMapping("/findbyexamid")
	@ResponseBody
	public Exam findbyexamid( String examid){
		
		log.info(examid+"��ѯ����");
		return es.findById(examid).get();
		
	}
	
	//���ӿ���
	@PostMapping("/addexam")
	@ResponseBody
	public Exam addexam(Exam exam) {
		log.info(exam.getExamId()+"���ӵĿ���id");
		es.addExam(exam);
		return exam;
	}
	//ɾ������
	@RequestMapping("/deleteexam")
	@ResponseBody
	public String deleteexam(String examid) {
		log.info(examid+"ɾ���Ŀ���id");
		
		if(es.delete(examid)) {
			log.info("ɾ���ɹ���");
			return "ɾ���ɹ���";
			
		}else {
			log.info("ɾ��ʧ�ܣ�");
			return "ɾ��ʧ�ܣ�";
		}
	}

	/*
	 * Ȩ��power
	 */
	// ��ʾ��ʦȨ��
@RequestMapping("/power")
public String power(Model model) {
	
	model.addAttribute("teachers",ts.findall());
	
	log.info("Ȩ�ޡ���������ʦ");
	
	
	return "admin/power";
}
	// ��ʾѧ��
@RequestMapping("/showstup")
@ResponseBody
public List<Student> showstup(Model model) {
	log.info("Ȩ��--ѧ��");
	return ss.findall();
	
}

	// �޸�ѧ��Ȩ��

@RequestMapping("/studentpower")
@ResponseBody
	public String studentpower(String powid,String studentid) {
	log.info("�޸�Ȩ��1"+studentid+"+++");
	Student stu = ss.quearyStudentById(studentid);
	
	log.info("�޸�Ȩ��2"+powid+"++++");
	Power pow = pd.findById(powid).get();
	//�ж�Ȩ���Ƿ�ı�
	if(stu.getPower().getPowerId()==powid){
		log.info("Ȩ��û��");
	}
	log.info("Ȩ���޸�");
	stu.setPower(pow);
	log.info("�޸�Ȩ��3");
	ss.save(stu);
	log.info("�޸�Ȩ��4");
	//ss.quearyStudentById(studentid).getPower().getPowerId()
	return ss.quearyStudentById(studentid).getPower().getPowerId();
	
}
	


	// �޸���ʦȨ��
@RequestMapping("/teacherpower")
@ResponseBody
	public String teacherpow(String powid,String teacherid) {
	log.info("�޸�Ȩ��1"+teacherid+"+++");
	Teacher teacher =ts.findById(teacherid);
	log.info("�޸�Ȩ��2"+powid+"++++");
	Power pow = pd.findById(powid).get();
	//�ж�Ȩ���Ƿ�ı�
	if(teacher.getPower().getPowerId()==powid){
		log.info("Ȩ��û��");
	}
	log.info("Ȩ���޸�");
	teacher.setPower(pow);
	log.info("�޸�Ȩ��3");
	ts.addTeacher(teacher);
	log.info("�޸�Ȩ��4");
//	ts.findById(teacherid).getPower().getPowerId()
	return ts.findById(teacherid).getPower().getPowerId();
	
	
	
	
}



}
