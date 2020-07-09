package com.exam.controller;

import java.awt.Window;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.entity.Exam;
import com.exam.entity.Question;
import com.exam.entity.Student;
import com.exam.entity.StudentExamAnswer;
import com.exam.entity.Teacher;
import com.exam.service.ExamService;
import com.exam.service.QuestionService;
import com.exam.service.StudentExamService;
import com.exam.service.StudentService;
import com.exam.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	
	
	@Autowired 
	private TeacherService ts;
	@Autowired
	private StudentExamService ses;

	@Autowired
	private StudentService ss;
	@Autowired
	private ExamService es;
	@Autowired
	private QuestionService qs;
	Log log =LogFactory.getLog(TeacherController.class);
	
	@Autowired
	private PasswordEncoder pe;
	
	
	//修改个人信息
	@RequestMapping("/updatexx")
	@ResponseBody
	public Teacher updatexx(String uid,String name,String major,String pwd ) {
		log.info(uid +"+++"+name+"+++"+pwd);
		Teacher teacher = ts.findById(uid);
		teacher.setTeacherName(name);
		teacher.setMajor(major);
		teacher.setPassword(pe.encode(pwd));
		ts.addTeacher(teacher);
		return teacher;
		
	}
	// 按学号查询学生
	@RequestMapping("/findstuid")
	@ResponseBody
	public Student quearyStudentById(String studentid) {
		log.info("查询id");
		Student st=ss.quearyStudentById(studentid);
		String bs64 = Base64.getEncoder().encodeToString(st.getPhoto());
		//暂时用密码字段装图片
		st.setPassword(bs64);
		return st;
	}

	
	// 按姓名查询学生（模糊查询）
	@RequestMapping("/findstuname")
	@ResponseBody
	public List<Student> quearyStudentByName(String studentname, Model model) {
		
		List<Student> st = ss.quearyStudentByName(studentname);
		for(Student stu :st ) {
			String bs64 = Base64.getEncoder().encodeToString(stu.getPhoto());
			//暂时用密码字段装图片
			stu.setPassword(bs64);	
		}
		return st;
	}


	//分页显示所有学生
	@RequestMapping("/showstudent")
	public String showstudent(int page,int size,Model model) {
		// int size=3;//一页10条记录
				log.info("0++");
				Page<Student> pae = ss.showPage(page, size);

//			Student stus= pae.getContent().get(0);
				ss.format(pae, model);
		return "admin/student";
	}
	
//考试信息
	@GetMapping("/showexam")
	public String queexam(Model model , int page, int size){
		
		   Page<Exam> pag= es.showexam(page, size);
		   log.info(pag);
			log.info(pag.getContent());
			if (pag.getContent().size() != 0) {
				log.info(pag.getContent().size() == 0);
				log.info(pag.getContent().get(0).getExamId()+"___试卷id");
				log.info(pag.getContent().get(0).getExamPla()+"___地点 ");
			}

			model.addAttribute("exams", pag);
			return "admin/exam";
		}

	//查看试题
	@RequestMapping("/showquestion")
	public String showquestion(Model model ,int page ,int size){
		Page<Question> que=	qs.showPage(page, size);
		model.addAttribute("questions", que);
		return "teacher/question";
	}
//添加试题
	@RequestMapping("/addquestion")
	@ResponseBody
	public  Question addquestion(String content ,String major){
		log.info(content+"添加的试题为");
		Question q=new Question();
		q.setQuestionId(qs.saveque(content ,major));
		//q.setQuestionId(qs.fingByContent(content));
		q.setMajor(major);
		q.setContent(content);
		log.info("添加完成");
		return q;
		
		
	}
	
//删除试题
	@RequestMapping("/deletequestion")
	@ResponseBody
	public String deletequestion(String questionid) {
		log.info(questionid+"llll---");
		Integer id = Integer.parseInt(questionid);
		log.info(id);
		if(qs.deletet(id)) {
			log.info("删除成功！");
			return "删除成功！";
			
		}else {
			log.info("fail");
			return "删除失败！";
		}
	}
//修改试题
	@RequestMapping("/updatequestion")
	@ResponseBody
	public Question updateque(Question question) {
		qs.updateQ(question);
			return question;
		
	}
	
	//按试题编号查找
	@RequestMapping("/findById")
	@ResponseBody
	public Question faindById(Integer questionid) {
		return qs.findById(questionid);
	}
	//按试题内容查找
	@RequestMapping("/findByContent")
	@ResponseBody
	public List<Question> faindByContent(String content) {
		log.info(content+"查找内容");
		return qs.fingByContent(content);
	}
	
	//增加考试
	@PostMapping("/addexam")
	@ResponseBody
	public Exam addexam(Exam exam) {
		log.info(exam.getExamId()+"增加的考试id");
		es.addExam(exam);
		return exam;
	}
	
	//查找考试
		@RequestMapping("/findbyexamid")
		@ResponseBody
		public Exam findbyexamid( String examid){
			
			log.info(examid+"查询考试");
			return es.findById(examid).get();
			
		}
	
	
	//查询未阅卷
	@RequestMapping("/querymarkpaper")
	@ResponseBody
	public List<String> querymarkpaper(){
		List<String> ls =ss.findmajor();
		for(int i=0;i<ls.size();i++) {
			log.info(ls.get(i)+"未阅卷");
		}
		return ls;
		
		
	}
	
	
	//阅卷
	@RequestMapping("/markpaper")
	public String markPaper(Model model,String major) {
		List<StudentExamAnswer> seaqList=ses.findallStudentExamAnswers(major);
		log.info(seaqList.size()+"阅卷");
		if(seaqList.size()!=0) {
			model.addAttribute("seas",seaqList);
		}else {
			
		}
		return "teacher/markingPaper";
	}
	//打分提交
	@PostMapping("/submitscore")
	@ResponseBody
	public String submitScore(@RequestBody List<Map<String, Object>> giveScore) {
		ses.giveScore(giveScore);
		
		return "提交成功！";
		
	}
	
	
}
