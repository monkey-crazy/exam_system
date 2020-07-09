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
	
	
	//�޸ĸ�����Ϣ
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


	//��ҳ��ʾ����ѧ��
	@RequestMapping("/showstudent")
	public String showstudent(int page,int size,Model model) {
		// int size=3;//һҳ10����¼
				log.info("0++");
				Page<Student> pae = ss.showPage(page, size);

//			Student stus= pae.getContent().get(0);
				ss.format(pae, model);
		return "admin/student";
	}
	
//������Ϣ
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

	//�鿴����
	@RequestMapping("/showquestion")
	public String showquestion(Model model ,int page ,int size){
		Page<Question> que=	qs.showPage(page, size);
		model.addAttribute("questions", que);
		return "teacher/question";
	}
//�������
	@RequestMapping("/addquestion")
	@ResponseBody
	public  Question addquestion(String content ,String major){
		log.info(content+"��ӵ�����Ϊ");
		Question q=new Question();
		q.setQuestionId(qs.saveque(content ,major));
		//q.setQuestionId(qs.fingByContent(content));
		q.setMajor(major);
		q.setContent(content);
		log.info("������");
		return q;
		
		
	}
	
//ɾ������
	@RequestMapping("/deletequestion")
	@ResponseBody
	public String deletequestion(String questionid) {
		log.info(questionid+"llll---");
		Integer id = Integer.parseInt(questionid);
		log.info(id);
		if(qs.deletet(id)) {
			log.info("ɾ���ɹ���");
			return "ɾ���ɹ���";
			
		}else {
			log.info("fail");
			return "ɾ��ʧ�ܣ�";
		}
	}
//�޸�����
	@RequestMapping("/updatequestion")
	@ResponseBody
	public Question updateque(Question question) {
		qs.updateQ(question);
			return question;
		
	}
	
	//�������Ų���
	@RequestMapping("/findById")
	@ResponseBody
	public Question faindById(Integer questionid) {
		return qs.findById(questionid);
	}
	//���������ݲ���
	@RequestMapping("/findByContent")
	@ResponseBody
	public List<Question> faindByContent(String content) {
		log.info(content+"��������");
		return qs.fingByContent(content);
	}
	
	//���ӿ���
	@PostMapping("/addexam")
	@ResponseBody
	public Exam addexam(Exam exam) {
		log.info(exam.getExamId()+"���ӵĿ���id");
		es.addExam(exam);
		return exam;
	}
	
	//���ҿ���
		@RequestMapping("/findbyexamid")
		@ResponseBody
		public Exam findbyexamid( String examid){
			
			log.info(examid+"��ѯ����");
			return es.findById(examid).get();
			
		}
	
	
	//��ѯδ�ľ�
	@RequestMapping("/querymarkpaper")
	@ResponseBody
	public List<String> querymarkpaper(){
		List<String> ls =ss.findmajor();
		for(int i=0;i<ls.size();i++) {
			log.info(ls.get(i)+"δ�ľ�");
		}
		return ls;
		
		
	}
	
	
	//�ľ�
	@RequestMapping("/markpaper")
	public String markPaper(Model model,String major) {
		List<StudentExamAnswer> seaqList=ses.findallStudentExamAnswers(major);
		log.info(seaqList.size()+"�ľ�");
		if(seaqList.size()!=0) {
			model.addAttribute("seas",seaqList);
		}else {
			
		}
		return "teacher/markingPaper";
	}
	//����ύ
	@PostMapping("/submitscore")
	@ResponseBody
	public String submitScore(@RequestBody List<Map<String, Object>> giveScore) {
		ses.giveScore(giveScore);
		
		return "�ύ�ɹ���";
		
	}
	
	
}
