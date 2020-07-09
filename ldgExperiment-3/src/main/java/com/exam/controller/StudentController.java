package com.exam.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.entity.Power;
import com.exam.entity.Question;
import com.exam.entity.Student;
import com.exam.entity.Teacher;
import com.exam.service.ExamService;
import com.exam.service.StudentExamService;
import com.exam.service.StudentService;

/**
 * 
 * @author "���"
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/student")
public class StudentController {
	Log log = LogFactory.getLog(StudentController.class);
	@Autowired
	private StudentExamService ses;
	@Autowired
	private StudentService ss;
	@Autowired
	private ExamService es;
	@Autowired
	private PasswordEncoder pe;

	@GetMapping("/login")
	public String login() {
		return "student/login";
	}

	
	//�޸ĸ�����Ϣ
	@RequestMapping("/updatexx")
	@ResponseBody
	public Student updatexx(String uid,String name,String major,String pwd ) {
		
		log.info(uid +"+++"+name+"+++"+pwd);
		Student stu = ss.quearyStudentById(uid);
		stu.setStudentName(name);
		stu.setMajor(major);
		stu.setPassword(pe.encode(pwd));
		ss.save(stu);
		return stu;
		
	}
	
	//
	
	/*
	 * ����ͼƬ����ʾ
	 */
	@RequestMapping("/showstu")
	@ResponseBody
	public String showPhoto(HttpServletRequest req) {
		Student ls = (Student)req.getSession().getAttribute("student");
		Map<String, Object> stuMap = new HashMap<String, Object>();// ����һ��map���������洢һ��ѧ������Ϣ
		stuMap.put("studentId", ls.getStudentId());
		stuMap.put("studentName", ls.getStudentName());
		log.info(ls.getStudentId());
		log.info(ls.getStudentName());
		// byte[] stu1 =lists.get(i).getPhoto() ;// ���ֽ�������ܴ����ݿ������ͼƬ���ֽ���
		String bs64 = Base64.getEncoder().encodeToString(ls.getPhoto());// ��Base64���ֽ�ת��ΪString
		stuMap.put("stuphoto", bs64);
		List<Map<String, Object>> stulist = new ArrayList<Map<String, Object>>();// �ü��ϴ洢ѧ����map
		log.info("ok");
		stulist.add(stuMap);
		req.getSession().setAttribute("stus", stulist);
		
		
			
		
		return ls.getPower().getPowerId();
		
	}

	@ResponseBody
	@RequestMapping("/reallogin")
	public String login(String id, String name, HttpServletRequest req) {

		Student stu = ss.findByIdName(id, name);
		req.getSession().removeAttribute("student");
		req.getSession().setAttribute("student", stu);
		req.getSession(false);
		Student student = (Student) req.getSession().getAttribute("student");

//		log.info(req.getSession().getId());
		return student.getStudentName();
	}

	// ��ʾ���п�����Ϣ
	@RequestMapping("/showAllExams")
	public String showAllExams(Model model) {
		model.addAttribute("exams", es.findall());
		return "student/exam";
	}

	// ��ʾ��ǰѧ���ı�����Ϣ
	@RequestMapping("/showAllStudentExam")
	@ResponseBody
	public List<Map<String, Object>> showAllStudentExam(HttpServletRequest req) {
		Student s = (Student) req.getSession().getAttribute("student");
		log.info(req.getSession().getId());
		List<Map<String, Object>> lists = ses.findAllStudentExam(s);
		if (lists.size() == 0) {
			return null;
		} else {
			return lists;
		}
	}
//ȥ����
	@RequestMapping("/toExam") // ȥ����/test/StuExam/toExam?studentId=20172178&studentName=Ldg&eid=1001
	public String toExam(String eid, HttpServletRequest req, Model model) {

		Student s = (Student) req.getSession().getAttribute("student");
		log.info(eid+"ȥ����");
		log.info(s.getStudentName()+"����");
		ses.addstudentExam(s, eid,req);
		log.info("1---");
		List<Question> papers = ses.showPaper(s, eid);
		log.info("2---");
		model.addAttribute("questions", papers);
		req.getSession().setAttribute("ExamId", eid);
		log.info("ok");
		// return "student/toexam";//����ҳ
		return "student/toexampage";
	}

	// ѧ������
	@RequestMapping("/baoming")
	@ResponseBody
	public String baoming(String examId, HttpServletRequest req) {
		Object o = req.getSession().getAttribute("student");
		log.info(req.getSession().getId());
		if (o == null)
			return "failsure";
		Student s = (Student) o;
		if (ses.findExam(s.getStudentId(), examId) != null) {
			return "exis";
		} else {
			ses.baoming(s, examId);
			return "success";
		}
	}

	@PostMapping("/submitPaper")
	@ResponseBody
	public String submitPaper(@RequestBody List<Map<String, Object>> answers, HttpServletRequest req) {
		// String s = "";
		Student s = (Student) req.getSession().getAttribute("student");
		String eid = (String) req.getSession().getAttribute("ExamId");

		ses.commit(s, eid, answers);
//		for (Map<String, Object> map : answers) {
//			s += map.get("queNo") + "***" + map.get("queAnswer")+ "***"+map.get("queBaseNo") ;
//		}
		// ɾ��׼����Ϣ
//		ses.deleteStudentExam(s.getStudentId(), eid);
		// ����ѧ������״̬
		ses.updateStudentExam(s.getStudentId(), eid);
		log.info(s + "***");
		return "����ɹ���";
	}

	// ѧ���ɼ���ѯ
	@GetMapping("/getScore")
	@ResponseBody
	public double getScore(String eid, HttpServletRequest req) {
		Student stut = (Student) req.getSession().getAttribute("student");
		String sid = stut.getStudentId();
		log.info(eid + "��ɼ�");
		log.info(sid + "��ɼ�");

		double sumscore = ses.sumScore(sid, eid);
		log.info("�ܳɼ���" + sumscore);
		return sumscore;
	}
}
