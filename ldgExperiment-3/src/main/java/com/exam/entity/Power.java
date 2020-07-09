package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 权限
 * 
 * 分为4级
 * 1级，查询 ，登陆
 * 2级 修改个人密码，     
 * 3级 增加试题      修改试题         报名考试 ,    查看考试信息   阅卷
 * 4级修改个人姓名，专业 ,  删除试题，修改考试信息，
 * 
 * 
 */
@Entity
@Table(name = "tb_power")
public class Power {

	@Id
	private String powerId;
	private String powerName;

	public String getPowerId() {
		return powerId;
	}

	public void setPowerId(String powerId) {
		this.powerId = powerId;
	}

	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

}
