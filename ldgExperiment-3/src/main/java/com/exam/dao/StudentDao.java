package com.exam.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.exam.entity.Student;

public interface StudentDao extends JpaRepository<Student, String> {

	public Student findByStudentIdAndStudentName(String id,String name);
	public Student findByStudentName(String name);
	
	public List<Student> findByStudentNameLike(String name );
	
	@Query(value = "SELECT `major` FROM `tb_student` GROUP BY `major`",nativeQuery = true)
	public List<String> findMajor();
}
