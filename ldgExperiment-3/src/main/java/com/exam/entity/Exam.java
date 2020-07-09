package com.exam.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/*
 * 
 * øº ‘±Ì
 *
 */
@Entity
@Table(name = "tb_exam")
public class Exam {
	@Id
	private String examId;//øº ‘id
	private String examPla;//øº ‘µÿµ„
	private Date examDate;//øº ‘ ±º‰

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getExamPla() {
		return examPla;
	}

	public void setExamPla(String examPla) {
		this.examPla = examPla;
	}

}
