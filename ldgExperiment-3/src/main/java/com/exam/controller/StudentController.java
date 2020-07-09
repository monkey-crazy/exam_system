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
 * @author "李栋贵"
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

	
	//修改个人信息
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
	 * 测试图片的显示
	 */
	@RequestMapping("/showstu")
	@ResponseBody
	public String showPhoto(HttpServletRequest req) {
		Student ls = (Student)req.getSession().getAttribute("student");
		Map<String, Object> stuMap = new HashMap<String, Object>();// 创建一个map对象用来存储一个学生的信息
		stuMap.put("studentId", ls.getStudentId());
		stuMap.put("studentName", ls.getStudentName());
		log.info(ls.getStudentId());
		log.info(ls.getStudentName());
		// byte[] stu1 =lists.get(i).getPhoto() ;// 用字节数组接受从数据库起初的图片的字节流
		String bs64 = Base64.getEncoder().encodeToString(ls.getPhoto());// 用Base64将字节转换为String
		stuMap.put("stuphoto", bs64);
		List<Map<String, Object>> stulist = new ArrayList<Map<String, Object>>();// 用集合存储学生的map
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

	// 显示所有考试信息
	@RequestMapping("/showAllExams")
	public String showAllExams(Model model) {
		model.addAttribute("exams", es.findall());
		return "student/exam";
	}

	// 显示当前学生的报名信息
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
//去考试
	@RequestMapping("/toExam") // 去考试/test/StuExam/toExam?studentId=20172178&studentName=Ldg&eid=1001
	public String toExam(String eid, HttpServletRequest req, Model model) {

		Student s = (Student) req.getSession().getAttribute("student");
		log.info(eid+"去考试");
		log.info(s.getStudentName()+"考试");
		ses.addstudentExam(s, eid,req);
		log.info("1---");
		List<Question> papers = ses.showPaper(s, eid);
		log.info("2---");
		model.addAttribute("questions", papers);
		req.getSession().setAttribute("ExamId", eid);
		log.info("ok");
		// return "student/toexam";//不分页
		return "student/toexampage";
	}

	// 学生报名
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
		// 删除准考信息
//		ses.deleteStudentExam(s.getStudentId(), eid);
		// 更新学生考试状态
		ses.updateStudentExam(s.getStudentId(), eid);
		log.info(s + "***");
		return "交卷成功！";
	}

	// 学生成绩查询
	@GetMapping("/getScore")
	@ResponseBody
	public double getScore(String eid, HttpServletRequest req) {
		Student stut = (Student) req.getSession().getAttribute("student");
		String sid = stut.getStudentId();
		log.info(eid + "查成绩");
		log.info(sid + "查成绩");

		double sumscore = ses.sumScore(sid, eid);
		log.info("总成绩：" + sumscore);
		return sumscore;
	}
}
