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
	//模糊查询
	//"%"+content+"%"   在mysql中需要加上%才能够执行模糊查询
	public List<Question>showContentLike(String content,int page,int size){
		return qd.findByContentLike("%"+content+"%", PageRequest.of(page, size));
		
	}
	//分页显示
	public Page<Question> showPage(int page,int size){
		return (qd.findAll(PageRequest.of(page, size)));
	}
	//分页并排序
	public Page<Question> showPageSort(int page,int size){
		return (qd.findAll(PageRequest.of(page, size,Sort.by(Direction.ASC,"questionId"))));
	}
	//更新的第一种使用save()
	//save方法会返回一个保存的对象
	@Transactional//回滚
	
	public Question update1(Question q) {
		return qd.save(q);
	}
	//第二种
	@Transactional
	public Boolean updateQ( Question qu) {
		int s=qd.update2(qu.getQuestionId(), qu.getContent());
		if(s==1) {
			return true;
		}else {
			return false;
		}
		
		
	}
	//保存
	@Transactional
	public int saveque(String context ,String major) {
		log.info(context+"试题");
		//log.info(qd.addquestion(context ,major));
		return qd.addquestion(context ,major);
	}
	//删除
	public boolean deletet(Integer id) {
		log.info("删除胡老师是："+id);
		try {
			qd.deleteById( id);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
		
	}
	//按id查找
	public Question findById(Integer id) {
		return qd.findById(id).get();
	}
	//按内容找
	public List<Question> fingByContent(String content){
		return  qd.findByContentLike("%"+content+"%");
	}
	
}
