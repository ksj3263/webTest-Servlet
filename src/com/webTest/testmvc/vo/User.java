package com.webTest.testmvc.vo;

public class User {
	private int u_idx;
	private String u_id;
	private String u_pw;
	private String u_name;
	private String u_tel;
	private String u_age;
	private String u_tel1;
	private String u_tel2;
	private String u_tel3;
	private int Rownum;
	
	public String getU_tel1() {
		return u_tel1;
	}
	public void setU_tel1(String u_tel1) {
		this.u_tel1 = u_tel1;
	}
	public String getU_tel2() {
		return u_tel2;
	}
	public void setU_tel2(String u_tel2) {
		this.u_tel2 = u_tel2;
	}
	public String getU_tel3() {
		return u_tel3;
	}
	public void setU_tel3(String u_tel3) {
		this.u_tel3 = u_tel3;
	}
	
	public int getU_idx() {
		return u_idx;
	}
	public void setU_idx(int u_idx) {
		this.u_idx = u_idx;
	}
	public String getU_id() {
		return u_id;
	}
	public void setU_id(String u_id) {
		this.u_id = u_id;
	}
	public String getU_pw() {
		return u_pw;
	}
	public void setU_pw(String u_pw) {
		this.u_pw = u_pw;
	}
	public String getU_name() {
		return u_name;
	}
	public void setU_name(String u_name) {
		this.u_name = u_name;
	}
	public String getU_tel() {
		return u_tel;
	}
	public void setU_tel(String u_tel) {
		this.u_tel = u_tel;
	}
	public String getU_age() {
		return u_age;
	}
	public void setU_age(String u_age) {
		this.u_age = u_age;
	}
	
	public void setRownum(int Rownum) {
		this.Rownum=Rownum;
	}
	public int getRownum() {
		return Rownum;
	}
	
}
