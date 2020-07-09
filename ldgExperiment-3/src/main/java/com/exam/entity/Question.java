package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/*
 * 试题实体
 */
@Entity
@Table(name = "tb_question")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//主键自动生成
	private Integer questionId;//试题id
	private String content;//试题内容
	private String major;
	
	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
