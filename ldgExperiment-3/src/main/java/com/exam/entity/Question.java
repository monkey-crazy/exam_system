package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/*
 * ����ʵ��
 */
@Entity
@Table(name = "tb_question")
public class Question {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//�����Զ�����
	private Integer questionId;//����id
	private String content;//��������
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
