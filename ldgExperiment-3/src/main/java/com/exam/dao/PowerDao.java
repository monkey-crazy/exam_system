package com.exam.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Power;

public interface PowerDao extends JpaRepository<Power, String> {

}
