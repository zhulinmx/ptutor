package com.google.prtutor.model;

public class Tb_records {
	
	private String id;
	private String words;
	
	public Tb_records() {
		super();
	}

	public Tb_records(String id, String Words)
	{
		super();
		this.id = id;
		this.words = Words;
	}
	public String getid() {
		return id;
	}

	public void setid(String id) {
		this.id = id;
	}
	public String getwords() {
		return words;
	}

	public void setwords(String words) {
		this.words = words;
	}
}
