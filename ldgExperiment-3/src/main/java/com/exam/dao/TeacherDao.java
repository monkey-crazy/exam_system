package com.exam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Teacher;

public interface TeacherDao extends JpaRepository<Teacher, String> {

}
