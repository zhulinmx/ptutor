package com.google.prtutor.model;

public class Tb_comment {
	private String createTime;
	private String comment;
	private String stuName;
	private int courseId;
	
	public Tb_comment(){
		super();
	}
	public Tb_comment(String comment,String createTime,String stuName,int courseId){
		super();
		this.comment = comment;
		this.createTime = createTime;
		this.stuName = stuName;
		this.courseId = courseId;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getStuName() {
		return stuName;
	}
	public void setStuName(String stuName) {
		this.stuName = stuName;
	}
}
