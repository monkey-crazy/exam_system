package com.exam.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.exam.dao.ExamDao;
import com.exam.dao.PaperDao;
import com.exam.dao.QuestionDao;
import com.exam.dao.RoleDao;
import com.exam.dao.StudentDao;
import com.exam.dao.StudentExamAnswerDao;
import com.exam.dao.StudentExamDao;
import com.exam.entity.Exam;
import com.exam.entity.Paper;
import com.exam.entity.Question;
import com.exam.entity.Student;
import com.exam.entity.StudentExam;
import com.exam.entity.StudentExamAnswer;
import com.exam.entity.StudentExamKeys;

@Service
public class StudentExamService {
	@Autowired
	private ExamDao ed;
	@Autowired
	private StudentDao sd;
	@Autowired
	private PaperDao pd;
	@Autowired
	private QuestionDao qd;
	@Autowired
	private StudentExamDao sed;
	@Autowired
	private StudentExamAnswerDao sead;
	@Autowired
	private RoleDao rd;
	@Autowired
	private StudentExamService ses;

	Log log = LogFactory.getLog(StudentExamService.class);

	private List<Question> findQuestions(int sum ,HttpServletRequest req) {
		Student st=(Student)req.getSession().getAttribute("student");
		String major=st.getMajor().toString();
		List<Question> ques = new ArrayList<Question>();
		int al1Records = (int) qd.majorcount(major); // tb_ question表的总记录数
		log.info(al1Records+"专业总数");
		int recordNo[] = new int[sum];
		java.util.Random ran = new java.util.Random();
		for (int i = 0; i < sum; i++) {
			int ranNo;
			while (true) {
				boolean flag = true;
				ranNo = ran.nextInt(al1Records);
				for (int j = 0; j < i; j++) {
					if (recordNo[j] == ranNo) {
						flag = false;
						break;
					}
				}
				if (flag)
					break;
			}
			recordNo[i] = ranNo;
		}
		for (int i = 0; i < sum; i++) {
			log.info(recordNo[i]);
			
			ques.add(qd.findByRecordNo(recordNo[i],major));
		}
		log.info("ok");
		return ques;
	}

	public void addstudentExam(Student s, String eid,HttpServletRequest req) {
		String paperId = "p_" + s.getStudentId() + "_" + eid;
		log.info(paperId+"试卷id");
		Paper paper = new Paper();
		paper.setPaperId(paperId);
		List<Question> qus = findQuestions(8,req);// qd.findAll();
		paper.setQuestion(qus);
		pd.save(paper);
		sed.updatePaperId(paperId, s.getStudentId(), eid);

	}

	public List<Question> showPaper(Student s, String eid) {
		String paperId = "p_" + s.getStudentId() + "_" + eid;
		return pd.findQuestionsById(paperId);
	}

	public void commit(Student s, String eid, List<Map<String, Object>> answers) {
		Exam exam = ed.findById(eid).get();
		for (Map<String, Object> map : answers) {
			StudentExamAnswer sea = new StudentExamAnswer();
			sea.setStudent(s);
			sea.setExam(exam);
			sea.setScore(-1);
//			Integer ss = Integer.parseInt(map.get("queBaseNo").toString());
//			log.info(ss + "**");
//			Question q = qd.findById(ss).get();
//			log.info(q);
//			sea.setQuestion(q);
//			log.info("kkkk");
//			sea.setAnswer((String) (map.get("queAnswer")));
			Integer qid=(Integer)map.get("questionId");
			log.info("第"+qid+"题");
			Question q=qd.getOne(qid);
			sea.setQuestion(q);
			sea.setAnswer((String)map.get("answer"));
			log.info("答案是："+(String)map.get("answer"));
			sead.save(sea);
		}

	}

	private List<Question> findQuestion(int sum) {
		List<Question> ques = new ArrayList<Question>();
		int allRecords = (int) qd.count(); // tbl_question表的总记录数
		int recordNo[] = new int[sum];
		java.util.Random ran = new java.util.Random();
		for (int i = 0; i < sum; i++) {
			int ranNo;
			while (true) {
				boolean flag = true;
				ranNo = ran.nextInt(allRecords);
				for (int j = 0; j < i; j++) {
					if (recordNo[j] == ranNo) {
						flag = false;
						break;
					}
				}
				if (flag)
					break;
			}
			recordNo[i] = ranNo;
		}
		for (int i = 0; i < sum; i++) {
//	      ques.add(qd.findByQuestions(recordNo[i]));
		}
		return ques;
	}

	public Student addStu(Student s) {
		// TODO Auto-generated method stub
		
		return sd.save(s);
	}

	public Exam addExam(Exam e) {
		// TODO Auto-generated method stub
		return ed.save(e);
	}

	public void baoming(Student s, String examId) {
		// TODO Auto-generated method stub
//		Exam e = ed.findById(examId).get();
//		Student ss = sd.findById(s.getStudentId()).get();
//		ss.getExams().add(e);
//		sd.save(ss);
		StudentExam sex = new StudentExam();
		sex.setExamId(examId);
		sex.setStudentId(s.getStudentId());
		sex.setFlag(0);
		
		sed.save(sex);
		
	}

	public List<Map<String, Object>> findAllStudentExam(Student s) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		List<StudentExam> studentExams = sed.findByStudentId(s.getStudentId());
		for (StudentExam se : studentExams) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("studentName", s.getStudentName());
			String examId = se.getExamId();
			Exam e = ed.findById(examId).get();
			map.put("examDate", e.getExamDate());
			map.put("examPla", e.getExamPla());
			map.put("examId", examId);
			map.put("score",se.getScore());
			map.put("flag", se.getFlag());
			log.info(se.getScore()+"初始成绩");
			lists.add(map);
		}
		return lists;
	}

	public StudentExam findExam(String studentId, String examId) {
		return sed.findByStudentIdAndExamId(studentId, examId);
	}

	public List<Student> findallStudent() {
		return sd.findAll();
	}

	public void updateStudentExam(String sid, String eid) {
		//删除学生报名信息
//		StudentExamKeys key = new StudentExamKeys();
//		key.setExamId(eid);
//		key.setStudentId(sid);
//		sed.deleteById(key);
		//更新学生考试状态
		sed.updateFlag(sid, eid, 1);

	}

	// 查询所有学生的答案
	public List<StudentExamAnswer> findallStudentExamAnswers(String major) {
		List<StudentExamAnswer> lists = sead.findAll();
		List<StudentExamAnswer> seas = new ArrayList<StudentExamAnswer>();
		for (StudentExamAnswer stus : lists) {
			if (stus.getScore() == -1) {
				seas.add(stus);
			}
		}
		return seas;
	}
	//批阅 修改成绩
	public void giveScore( List<Map<String , Object>> giveScore) {
		for (Map<String, Object> map: giveScore) {
			String sid =(String)map.get("stuId");
			log.info(sid+"sid");
			String eid=(String ) map.get("examId");
			log.info(eid+"eid");
			Integer qid = Integer.parseInt((String)map.get("queId"));
			log.info(qid+"qid");
			double score =Double.parseDouble((String)map.get("score"));
			log.info(score);
			int s=sead.updateScore(sid, eid, qid, score);
			log.info(s+"修改成绩");
		}
		
	}
	//计算总成绩
	public double sumScore(String sid,String eid) {
		double score=0.0;
//		StudentExamKeys key = new StudentExamKeys();
//		key.setExamId(eid);
//		key.setStudentId(sid);
//		StudentExam  se= sed.findById(key).get();
		List<StudentExamAnswer> seas=sead.findByStudenIdAndExamId(sid, eid);
		for(StudentExamAnswer sea:seas) {
			if(sea.getScore()==-1) {
				return -1;
				
				}
			//log.info(sea.getScore()+"每题成绩");
			score+=sea.getScore();
			
		}
		log.info("总成绩"+score);
		//保存成绩到studentExam里面
		String paperid= "p_" + sid + "_" + eid;
		int o=sed.updateSumScore(sid, eid, paperid, score);
		log.info(o+"修改总成绩");
		return score;
	}

}
