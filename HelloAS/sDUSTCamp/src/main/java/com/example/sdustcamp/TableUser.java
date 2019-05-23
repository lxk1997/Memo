package com.example.sdustcamp;

import cn.bmob.v3.BmobObject;

public class TableUser extends BmobObject {
	private String phoneNum;
	private String password;
	
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getPhoneNum() {
		return this.phoneNum;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return this.password;
	}
	
}
