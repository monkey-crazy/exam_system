package com.exam.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.exam.dao.PowerDao;
import com.exam.dao.RoleDao;
import com.exam.dao.TeacherDao;
import com.exam.entity.Power;
import com.exam.entity.Role;
import com.exam.entity.Student;
import com.exam.entity.Teacher;
import com.exam.service.StudentExamService;
import com.example.demo.LdgExperiment3Application.IsStu;

@Controller
public class RegixController {

	@Autowired
	private RoleDao rd;
	@Autowired
	private PowerDao pd;
	@Autowired
	private StudentExamService ses;
	@Autowired
	private PasswordEncoder pe;
	@Autowired
	private IsStu is;
	@Autowired
	private TeacherDao td;

	/*
	 * ע��ѧ��
	 */
	Log log = LogFactory.getLog(RegixController.class);

	@RequestMapping("/studentregix")
	public String addStudent(@Valid Student stu, BindingResult br, @RequestParam("stuphoto") MultipartFile photo,
			Model model) throws Exception {

		if (br.hasErrors()) {

			Map<String, String> err = new HashMap<String, String>();
			if (br.hasFieldErrors("studentId")) {

				err.put("studentIdError", br.getFieldError("studentId").getDefaultMessage());
			}
			if (br.hasFieldErrors("studentName")) {

				err.put("studentNameError", br.getFieldError("studentName").getDefaultMessage());
			}
			if (br.hasFieldErrors("password")) {

				err.put("passwordError", br.getFieldError("password").getDefaultMessage());
			}

			model.addAttribute("errors", err);
			return "student/regix";
		}
		if (photo == null || photo.getBytes().length == 0) {
			throw new Exception("��Ƭ����Ϊ��");
		}
		
		stu.setPhoto(photo.getBytes());// ���ļ�ת��Ϊ�ֽ�����ŵ�stu
		// ��������
		
		String password =pe.encode(stu.getPassword());
		stu.setPassword(password);
		// ���ע��Ľ�ɫadd(rd.getOne("student"))
		List<Role> rol =new ArrayList<Role>();

		log.info(rd.getOne("student").toString());
		rol.add(rd.getOne("student"));
		log.info(rol.toString());
		
		//���Ȩ��
		
		Power pow =pd.getOne("2");
		
		stu.setRoles(rol);
		stu.setPower(pow);
	log.info(stu.getRoles().toString());

		ses.addStu(stu);// ����ѧ�������ݿ�
		return "login";
	}

	@RequestMapping("/index")
	public String index(HttpServletRequest req ,Model model) {
		Authentication au = SecurityContextHolder.getContext().getAuthentication();
		log.info(au.getAuthorities());
		log.info(au.getCredentials());
		log.info(au.getDetails()+"1");
		if (au.getPrincipal() instanceof Student) {
			Student student = (Student) au.getPrincipal();
			log.info(student.getPhoto());
			String bs64 = Base64.getEncoder().encodeToString(student.getPhoto());
			log.info(student.getStudentId());
			log.info(student.getStudentName());
			req.getSession().setAttribute("student", student);
			model.addAttribute("photo",bs64);
			log.info(student.getRoles());
		} else if (au.getPrincipal() instanceof Teacher) {
			Teacher teacher = (Teacher) au.getPrincipal();
			
			
			req.getSession().setAttribute("teacher", teacher);
			log.info(au.getPrincipal() + "��ʦ");
			
			log.info(td.findById(au.getName()).get().getPower().getPowerId()+"Ȩ��");
		} else {
			req.getSession().setAttribute("admin", au.getPrincipal());
			log.info(au.getPrincipal() + "����Ա");
		}
		return "index";
	}
	@GetMapping("/login")
	public String login() {
		return "login";
	}

}
