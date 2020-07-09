package com.exam.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.StudentExam;
import com.exam.entity.StudentExamKeys;

public interface StudentExamDao extends JpaRepository<StudentExam, StudentExamKeys> {
	@Transactional
	@Modifying
	@Query("update StudentExam se set se.paper.paperId=?1 where se.studentId=?2 and se.examId=?3")
	public int updatePaperId(String pid, String sid, String eid);
	public StudentExam findByStudentIdAndExamId(String studentId,String examId);
	public List<StudentExam> findByStudentId(String studentId);
	@Transactional
	@Modifying
	@Query(value = "update `student_exam` se set se.flag=?3 where se.student_id =?1 and se.exam_id=?2" ,nativeQuery = true)
	public int updateFlag(String sid,String eid ,int flag);
	
	
	@Transactional
	@Modifying
	@Query(value = "update student_exam se set se.score=?3 where se.student_id =?1 and se.exam_id=?2" ,nativeQuery = true)
	public int updateScore(String sid,String eid ,double score);
	//更改总成绩
	@Transactional
	@Modifying
	@Query(value = "update student_exam se set se.score=?4 where se.student_id =?1 and se.exam_id=?2 and paper_id=?3" ,nativeQuery = true)
	public int updateSumScore(String sid,String eid,String pid ,double score);
}
