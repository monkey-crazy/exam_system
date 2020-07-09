package com.exam.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
/*
 * �Ծ�ʵ��
 */
@Entity
@Table(name = "tb_paper")
public class Paper {
	@Id
	private String paperId;//�Ծ�id
	@ManyToMany
	@JoinTable(name="make_paper",joinColumns = @JoinColumn(name="paper_id"),inverseJoinColumns = @JoinColumn(name="question_id"))
	private List<Question> questions;//����
	public String getPaperId() {
		return paperId;
	}
	public void setPaperId(String paperId) {
		this.paperId = paperId;
	}
	public List<Question> getQuestion() {
		return questions;
	}
	public void setQuestion(List<Question> question) {
		this.questions = question;
	}
	

}
