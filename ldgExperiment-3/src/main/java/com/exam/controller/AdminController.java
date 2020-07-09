
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
	 * 学生
	 */
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

	// 显示所有学生信息
	@GetMapping("/findAll")
	public String quearyStudentAll(Model model, int page, int size) {
		// int size=3;//一页10条记录
		log.info("0++");
		Page<Student> pae = ss.showPage(page, size);

//	Student stus= pae.getContent().get(0);
		ss.format(pae, model);

		return "admin/student";
	}

	// 删除学生
	@RequestMapping(value = "/deletestudent")
	@ResponseBody
	public String deletestudent(String stuid) {
			log.info(stuid+"删除的学生id");
		
		if(ss.deletStudent(stuid)) {
			log.info("删除成功！");
			return "删除成功！";
			
		}else {
			log.info("fail");
			return "删除失败！";
		}
	}

	// 修改学生信息
	@RequestMapping("/updatestu")
	@ResponseBody
	public Student updstudent(String stuid, String stuname)
			throws Exception {
		// 通过学号查到学生信息
		Student st = ss.quearyStudentById(stuid);

		// 将修改的信息替换
		st.setStudentName(stuname);
		
		// 然后重新保存。
	return 	ss.save(st);
		

	}

//	//
//	/*
//	 * 老师
//	 */
	//查询老师
	@RequestMapping("/findbyid")
	@ResponseBody
	public Teacher queteacher1( String teacherid){
		log.info(teacherid+"查询");
		return ts.findById(teacherid);
	}
	// 显示所有老师信息
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

	//修改老师信息
	@RequestMapping("/updateteacher")
	@ResponseBody
	public Teacher updteacher(String teacherId,String teacherName, String major) {
		Teacher te=ts.findById(teacherId);
		te.setTeacherName(teacherName);
		te.setMajor(major);
		log.info("修改的老师为："+te.getTeacherName());
		ts.addTeacher(te);
		return te;
	}
	// 增加老师
	@PostMapping("/addteacher")
	@ResponseBody
	public Teacher addteacher(Teacher teacher) {

		String passw = pe.encode(teacher.getPassword());
		teacher.setPassword(passw);
		

		
		// 添加注册的角色add(rd.getOne("student"))
		List<Role> rol =new ArrayList<Role>();

		log.info(rd.getOne("teacher").toString());
		rol.add(rd.getOne("teacher"));
		log.info(rol.toString());
		
		//添加权限
		Power pow =pd.getOne("2");
		log.info(pow.getPowerName()+"初始的权限");
		teacher.setRoles(rol);
		teacher.setPower(pow);
		
		
		log.info(teacher);
		log.info(teacher.getTeacherName());
		log.info(teacher.getTeacherId()+"888888");
		
		return ts.addTeacher(teacher);

	}
	//删除老师
	@RequestMapping("/deleteteacher")
	@ResponseBody
	public String delteacher(String teacherid) {
		log.info(teacherid);
		
		if(ts.deletet(teacherid)) {
			log.info("删除成功！");
			return "删除成功！";
			
		}else {
			log.info("fail");
			return "删除失败！";
		}
	}
//	/*
//	 * 试卷
//	 */
	//查看所有考试
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

   //查找考试
	@RequestMapping("/findbyexamid")
	@ResponseBody
	public Exam findbyexamid( String examid){
		
		log.info(examid+"查询考试");
		return es.findById(examid).get();
		
	}
	
	//增加考试
	@PostMapping("/addexam")
	@ResponseBody
	public Exam addexam(Exam exam) {
		log.info(exam.getExamId()+"增加的考试id");
		es.addExam(exam);
		return exam;
	}
	//删除考试
	@RequestMapping("/deleteexam")
	@ResponseBody
	public String deleteexam(String examid) {
		log.info(examid+"删除的考试id");
		
		if(es.delete(examid)) {
			log.info("删除成功！");
			return "删除成功！";
			
		}else {
			log.info("删除失败！");
			return "删除失败！";
		}
	}

	/*
	 * 权限power
	 */
	// 显示老师权限
@RequestMapping("/power")
public String power(Model model) {
	
	model.addAttribute("teachers",ts.findall());
	
	log.info("权限————老师");
	
	
	return "admin/power";
}
	// 显示学生
@RequestMapping("/showstup")
@ResponseBody
public List<Student> showstup(Model model) {
	log.info("权限--学生");
	return ss.findall();
	
}

	// 修改学生权限

@RequestMapping("/studentpower")
@ResponseBody
	public String studentpower(String powid,String studentid) {
	log.info("修改权限1"+studentid+"+++");
	Student stu = ss.quearyStudentById(studentid);
	
	log.info("修改权限2"+powid+"++++");
	Power pow = pd.findById(powid).get();
	//判断权限是否改变
	if(stu.getPower().getPowerId()==powid){
		log.info("权限没变");
	}
	log.info("权限修改");
	stu.setPower(pow);
	log.info("修改权限3");
	ss.save(stu);
	log.info("修改权限4");
	//ss.quearyStudentById(studentid).getPower().getPowerId()
	return ss.quearyStudentById(studentid).getPower().getPowerId();
	
}
	


	// 修改老师权限
@RequestMapping("/teacherpower")
@ResponseBody
	public String teacherpow(String powid,String teacherid) {
	log.info("修改权限1"+teacherid+"+++");
	Teacher teacher =ts.findById(teacherid);
	log.info("修改权限2"+powid+"++++");
	Power pow = pd.findById(powid).get();
	//判断权限是否改变
	if(teacher.getPower().getPowerId()==powid){
		log.info("权限没变");
	}
	log.info("权限修改");
	teacher.setPower(pow);
	log.info("修改权限3");
	ts.addTeacher(teacher);
	log.info("修改权限4");
//	ts.findById(teacherid).getPower().getPowerId()
	return ts.findById(teacherid).getPower().getPowerId();
	
	
	
	
}



}

