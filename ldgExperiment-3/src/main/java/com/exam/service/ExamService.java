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
	//ͨ��id��ѯ����
	public Optional<Exam> findById(String examId) {
		return ed.findById(examId);
	}
	//���ӿ���
	public void addExam(Exam exam ) {
		ed.save(exam);
	}
	//��ҳ��ѯ
	public Page<Exam> showexam(int page,int size){
		return (ed.findAll(PageRequest.of(page, size)));
	}
	//ɾ������
	public boolean delete(String examid) {
    try {
		ed.deleteById(examid);
		log.info(examid+"��ɾ����id");
		return true;
	} catch (Exception e) {
		// TODO: handle exception
		return false;
	}
    }
}
