package com.exam.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Question;
import com.exam.entity.Student;

public interface QuestionDao extends JpaRepository<Question, Integer> {

//	ģ����ѯlike
	List<Question> findByContentLike(String content,Pageable pa);
	//���²����ڶ��ַ�������ȷҪ�޸ĵĲ���
	@Modifying//����ֵֻ��ʱInteger����void
	@Query("update Question q set q.content=?2 where questionId=?1")
	public int update2(Integer questionId,String content);
	//`tb_question`���ӷ����������ݿ����޷�ִ��
	@Query(value="SELECT que.* FROM (SELECT * FROM `tb_question` WHERE major= ?2) que  LIMIT ?1, 1 ;", nativeQuery=true)
	public Question findByRecordNo(int x,String y);

	//��������
	@Modifying//����ֵֻ��ʱInteger����void
	@Query( value = "INSERT INTO `tb_question`(content,major) VALUES(?1,?2)" , nativeQuery = true)
	public int addquestion(String context ,String major);
	
	@Query(value = "SELECT COUNT(*) FROM `tb_question` WHERE major=?1",nativeQuery = true)
	public int majorcount(String major);
	
	//ģ����ѯ����
	
	public List<Question> findByContentLike(String content );
}
