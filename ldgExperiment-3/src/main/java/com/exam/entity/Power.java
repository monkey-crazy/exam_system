package com.exam.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Ȩ��
 * 
 * ��Ϊ4��
 * 1������ѯ ����½
 * 2�� �޸ĸ������룬     
 * 3�� ��������      �޸�����         �������� ,    �鿴������Ϣ   �ľ�
 * 4���޸ĸ���������רҵ ,  ɾ�����⣬�޸Ŀ�����Ϣ��
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
