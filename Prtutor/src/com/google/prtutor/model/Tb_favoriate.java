package com.google.prtutor.model;

public class Tb_favoriate {
	
	private int courseId;
	private String name;
	
	public Tb_favoriate(){
		super();
	}
	public Tb_favoriate(int courseId, String name){
		super();
	    this.courseId = courseId;
	    this.name = name ;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}	
}
