package com.exam.service;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.exam.dao.QuestionDao;
import com.exam.entity.Question;

@Service
public class QuestionService {
	@Autowired
	private QuestionDao qd;
	
	Log log =LogFactory.getLog(QuestionService.class);

	public Question saveQ(Question q) {
		return qd.save(q);
	}
	public List<Question> findAll(){
		return qd.findAll();
	}
	//ģ����ѯ
	//"%"+content+"%"   ��mysql����Ҫ����%���ܹ�ִ��ģ����ѯ
	public List<Question>showContentLike(String content,int page,int size){
		return qd.findByContentLike("%"+content+"%", PageRequest.of(page, size));
		
	}
	//��ҳ��ʾ
	public Page<Question> showPage(int page,int size){
		return (qd.findAll(PageRequest.of(page, size)));
	}
	//��ҳ������
	public Page<Question> showPageSort(int page,int size){
		return (qd.findAll(PageRequest.of(page, size,Sort.by(Direction.ASC,"questionId"))));
	}
	//���µĵ�һ��ʹ��save()
	//save�����᷵��һ������Ķ���
	@Transactional//�ع�
	
	public Question update1(Question q) {
		return qd.save(q);
	}
	//�ڶ���
	@Transactional
	public Boolean updateQ( Question qu) {
		int s=qd.update2(qu.getQuestionId(), qu.getContent());
		if(s==1) {
			return true;
		}else {
			return false;
		}
		
		
	}
	//����
	@Transactional
	public int saveque(String context ,String major) {
		log.info(context+"����");
		//log.info(qd.addquestion(context ,major));
		return qd.addquestion(context ,major);
	}
	//ɾ��
	public boolean deletet(Integer id) {
		log.info("ɾ������ʦ�ǣ�"+id);
		try {
			qd.deleteById( id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	//��id����
	public Question findById(Integer id) {
		return qd.findById(id).get();
	}
	//��������
	public List<Question> fingByContent(String content){
		return  qd.findByContentLike("%"+content+"%");
	}
	
}
