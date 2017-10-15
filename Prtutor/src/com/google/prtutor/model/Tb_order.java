package com.google.prtutor.model;

public class Tb_order {

	private int courseId;
	private String username;
	private String odstage;
	private String createTime;

	public Tb_order() {
		super();
	}

	public Tb_order(int courseId, String username, String odstage, String createTime) {
		super();
		this.courseId = courseId;
		this.username = username;
		this.odstage = odstage;
		this.createTime = createTime;

	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getOdstage() {
		return odstage;
	}

	public void setOdstage(String odstage) {
		this.odstage = odstage;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
