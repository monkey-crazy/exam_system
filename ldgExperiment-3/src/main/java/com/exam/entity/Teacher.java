package com.exam.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "tb_teacher")
public class Teacher implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Size(max = 11,min = 4)
	@Id
	private String teacherId;

	@Size(max = 12,min = 1)
	private String teacherName;
	@Size(min = 6)
	private String password;
	private String major;// 专业

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "teacher_role", joinColumns = @JoinColumn(name = "teacher_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles = new ArrayList<>();

	//teacher的权限
	
	@ManyToOne
	@JoinColumn(name = "power_id")
	//@ColumnDefault("4")
	private Power power;
	
		



	public Power getPower() {
		return power;
	}

	public void setPower(Power power) {
		this.power = power;
	}

	

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<SimpleGrantedAuthority> auths = new ArrayList<SimpleGrantedAuthority>();
		for (Role r : roles) {
			SimpleGrantedAuthority sga = new SimpleGrantedAuthority(r.getRolename());
			auths.add(sga);
		}
		return auths;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.teacherId;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
