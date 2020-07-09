package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.ColumnDefault;
/*
 * 做题
 */
@Entity
@Table(name = "student_exam_answer")
public class StudentExamAnswer {
	@Id
//	@GeneratedValue(generator = "qus")
//	@SequenceGenerator(name = "qus", allocationSize = 1)
//TRUNCATE TABLE `student_exam_answer`重置数据库主键值
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "qus")
	@SequenceGenerator(name = "qus", sequenceName = "seq_payment",initialValue=1,allocationSize = 1)
	private Integer stuExamAnswerId;
	@ManyToOne
	@JoinColumn(name = "student_id")
	private Student student;
	@ManyToOne
	@JoinColumn(name = "question_id")
	private Question question;
	@ManyToOne
	@JoinColumn(name = "exam_id")
	private Exam exam;
	private String answer;
	@ColumnDefault("0.0")
	private double score;

	public Integer getStuExamAnswerId() {
		return stuExamAnswerId;
	}

	public void setStuExamAnswerId(Integer stuExamAnswerId) {
		this.stuExamAnswerId = stuExamAnswerId;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
}
