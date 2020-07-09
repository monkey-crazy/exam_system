package com.exam.entity;

import java.io.Serializable;

public class StudentExamKeys implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private String studentId;
	private String examId;

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
}
