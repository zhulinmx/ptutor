package com.google.prtutor.model;

public class Tb_city {
	public String name;
	public String pinyi;

	public Tb_city(String name, String pinyi) {
		super();
		this.name = name;
		this.pinyi = pinyi;
	}

	public Tb_city() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPinyi() {
		return pinyi;
	}

	public void setPinyi(String pinyi) {
		this.pinyi = pinyi;
	}
}
