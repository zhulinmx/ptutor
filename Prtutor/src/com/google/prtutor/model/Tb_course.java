package com.google.prtutor.model;

public class Tb_course {
	
	private String type;
	private String start_day;
	private String finish_day;
	private String course_time;
	private int classId;
	private String courseName;
	private String desc;
	private float price;
	private int container;
	private int betday;
	private int time;
	private int total;
	private int enrollment;
	private int courseImg;
	private int longitude;
	private int latitude;
	private String courseClass;
	private int teachid;
	
	public Tb_course() {
		super();
	}
	public Tb_course(String courseName, String type)
	{
		super();
		this.courseName = courseName;
		this.type = type;
	}
	public Tb_course(String courseName, String type,String start_day,String course_time,float price,int container,int betday,int total,String desc,String courseClass,int teachid)
	{
		super();
		this.courseName = courseName;
		this.type = type;
		this.start_day = start_day;
		this.course_time = course_time;
		this.container = container;
		this.betday = betday;
		this.total = total;
		this.price = price;
		this.desc = desc;
		this.courseClass = courseClass;
		this.teachid = teachid;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
   
	public String getStart_day() {
		return start_day;
	}

	public void setStart_day(String start_day) {
		this.start_day = start_day;
	}

	public String getFinish_day() {
		return finish_day;
	}

	public void setFinish_day(String finish_day) {
		this.finish_day = finish_day;
	}

	public String getCourse_time() {
		return course_time;
	}

	public void setCourse_time(String course_time) {
		this.course_time = course_time;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getContainer() {
		return container;
	}

	public void setContainer(int container) {
		this.container = container;
	}

	public int getEnrollment() {
		return enrollment;
	}

	public void setEnrollment(int enrollment) {
		this.enrollment = enrollment;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getBetday() {
		return betday;
	}
	public void setBetday(int betday) {
		this.betday = betday;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getCourseImg() {
		return courseImg;
	}
	public void setCourseImg(int courseImg) {
		this.courseImg = courseImg;
	}
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public String getCourseClass() {
		return courseClass;
	}
	public void setCourseClass(String courseClass) {
		this.courseClass = courseClass;
	}
	public int getTeachid() {
		return teachid;
	}
	public void setTeachid(int teachid) {
		this.teachid = teachid;
	} 
}
