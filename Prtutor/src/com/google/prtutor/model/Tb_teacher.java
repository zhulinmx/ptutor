package com.google.prtutor.model;

public class Tb_teacher {
	private int _id;
	private String city;
	private String teachName;
	private String realName;
	private String pwd;
	private int image;
	private String phone;
	private String desc;
	private int stuNum;
	private int fans;
	private String quotation;
	private float estimate;
	private String feature;
	private int teachAge;
	private String sex;
	private String address;
	private int longitude;
	private int latitude;
	
	public Tb_teacher(){
		super();
	}
	public Tb_teacher(String pwd,String teachName,String phone){
		super();
		this.pwd = pwd;
		this.teachName = teachName;
		this.phone = phone;
	}
	public int getid() {
		return _id;
	}

	public void setid(int id) {
		this._id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTeachName() {
		return teachName;
	}
	public void setTeachName(String teachName) {
		this.teachName = teachName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public int getStuNum() {
		return stuNum;
	}
	public void setStuNum(int stuNum) {
		this.stuNum = stuNum;
	}
	public int getFans() {
		return fans;
	}
	public void setFans(int fans) {
		this.fans = fans;
	}
	public String getQuotation() {
		return quotation;
	}
	public void setQuotation(String quotation) {
		this.quotation = quotation;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public float getEstimate() {
		return estimate;
	}
	public void setEstimate(float estimate) {
		this.estimate = estimate;
	}
	public int getTeachAge() {
		return teachAge;
	}
	public void setTeachAge(int teachAge) {
		this.teachAge = teachAge;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}

}
