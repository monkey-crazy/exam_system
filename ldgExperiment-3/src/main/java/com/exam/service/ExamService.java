package com.exam.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.exam.dao.ExamDao;
import com.exam.entity.Exam;

@Service
public class ExamService {

	@Autowired
	private ExamDao ed;
	Log log = LogFactory.getLog(ExamService.class);
	public List<Exam> findall(){
		return ed.findAll();
	}
	//通过id查询考试
	public Optional<Exam> findById(String examId) {
		return ed.findById(examId);
	}
	//增加考试
	public void addExam(Exam exam ) {
		ed.save(exam);
	}
	//分页查询
	public Page<Exam> showexam(int page,int size){
		return (ed.findAll(PageRequest.of(page, size)));
	}
	//删除考试
	public boolean delete(String examid) {
    try {
		ed.deleteById(examid);
		log.info(examid+"已删除的id");
		return true;
	} catch (Exception e) {
		// TODO: handle exception
		return false;
	}
    }
}
