package com.exam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Paper;
import com.exam.entity.Question;

public interface PaperDao extends JpaRepository<Paper, String> {
	@Query("select p.questions from Paper p where p.paperId=?1")
	public List<Question> findQuestionsById(String pid);
}
