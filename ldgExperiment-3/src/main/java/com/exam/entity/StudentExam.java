package com.exam.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
/*
 * Ñ§Éú¿¼ÊÔ
 */
@Entity
@Table(name = "student_exam")
@IdClass(StudentExamKeys.class)
public class StudentExam {

	@Id
	@Column(name = "student_id", length = 30)
	private String studentId;
	@Id
	@Column(name = "exam_id", length = 30)
	private String examId;
	@ColumnDefault("-1")
	private double score;
	@OneToOne
	@JoinColumn(name = "paper_id")
	private Paper paper;
	@ColumnDefault("0")
	private int flag;

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public double getScore() {
		return score;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
