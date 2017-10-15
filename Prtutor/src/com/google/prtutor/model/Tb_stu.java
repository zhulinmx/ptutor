package com.google.prtutor.model;

public class Tb_stu {
	private int _id; // �û�id
	private String pwd; // ����
	private String city; // ����
	private String stage; // ѧԱ��ݣ�ѧϰ�׶�
	private String phone; // �ֻ���
	private String image; // ͷ��url
	private String sex; // �Ա�
	private String birth; // ��������
	private String address; // סַ
	private String stuname; // ѧԱ����

	public Tb_stu() {
		super();
	}
	
	public Tb_stu(String pwd,String stuname,String phone) {
		super();
		this.pwd = pwd;
		this.stuname = stuname;
		this.phone = phone;
	}

	public Tb_stu(int id, String pwd, String city, String stage, String phone, String image, String sex, String birth,
			String address, String stuname)
	{
		super();
		this._id = id;
		this.pwd = pwd;
		this.city = city;
		this.stage = stage;
		this.stage = stage;
		this.phone = phone;
		this.setImage(image);
		this.setSex(sex);
		this.birth = birth;
		this.address = address;
		this.stuname = stuname;
	}

	public int getid() {
		return _id;
	}

	public void setid(int id) {
		this._id = id;
	}

	public String getpwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getcity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getstage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getphone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getbirth() {
		return birth;
	}

	public void setbirth(String birth) {
		this.birth = birth;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStuname() {
		return stuname;
	}

	public void setStuname(String stuname) {
		this.stuname = stuname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
