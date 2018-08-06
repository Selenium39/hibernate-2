package com.wantao.bean;

import java.util.Date;

/*
 *@author:wantao
 *@time:Jul 29, 2018 2:10:15 PM
 *@description:和数据库里面的表一一对应,必须符合javaBean规范
 */
public class Student {
	private Integer id;
	private String name;
	private String sex;
	private Date regDate;

	public Student() {
		super();
	}

	public Student(String name, String sex, Date regDate) {
		super();
		this.name = name;
		this.sex = sex;
		this.regDate = regDate;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", sex=" + sex + ", regDate=" + regDate + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

}
