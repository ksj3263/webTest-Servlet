package com.webTest.testmvc.vo;

public class Board {
	private int b_idx;
	private String b_title;
	private String b_content;
	private String b_date;
	private String b_writer;
	private int u_idx;
	private int Rownum;
	private int b_order;
	private int b_base;
	private int b_layer;
	
	public int getB_layer() {
		return b_layer;
	}
	public void setB_layer(int b_layer) {
		this.b_layer = b_layer;
	}
	public int getB_idx() {
		return b_idx;
	}
	public void setB_idx(int b_idx) {
		this.b_idx = b_idx;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public String getB_date() {
		return b_date;
	}
	public void setB_date(String b_date) {
		this.b_date = b_date;
	}
	public String getB_writer() {
		return b_writer;
	}
	public void setB_writer(String b_writer) {
		this.b_writer = b_writer;
	}
	public int getU_idx() {
		return u_idx;
	}
	public void setU_idx(int u_idx) {
		this.u_idx = u_idx;
	}
	public int getRownum() {
		return Rownum;
	}
	public void setRownum(int rownum) {
		Rownum = rownum;
	}
	public int getB_order() {
		return b_order;
	}
	public void setB_order(int b_order) {
		this.b_order = b_order;
	}
	public int getB_base() {
		return b_base;
	}
	public void setB_base(int b_base) {
		this.b_base = b_base;
	}	
}
