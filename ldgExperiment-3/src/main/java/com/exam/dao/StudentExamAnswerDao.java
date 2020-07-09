package com.exam.dao;

import java.util.List;

import org.hibernate.type.TrueFalseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.exam.entity.StudentExamAnswer;

public interface StudentExamAnswerDao extends JpaRepository<StudentExamAnswer, Integer> {
	@Transactional
	@Modifying
	@Query(value = "update `student_exam_answer` sea set score=?4 where sea.student_id=?1 and sea.question_id=?3 and sea.exam_id=?2 ", nativeQuery = true)
	public int updateScore(String sid, String eid, Integer qid, double score);

	@Query(value = "select * from `student_exam_answer` sea where sea.student_id=?1 and sea.exam_id=?2", nativeQuery = true)
	public List<StudentExamAnswer> findByStudenIdAndExamId(String sid, String eid);

}
