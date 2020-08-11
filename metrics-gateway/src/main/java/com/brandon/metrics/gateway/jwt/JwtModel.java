package com.brandon.metrics.gateway.jwt;

import java.util.List;

public class JwtModel {

	private String userName;
	
	private List<String> roleList;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public List<String> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<String> roleList) {
		this.roleList = roleList;
	}

	public JwtModel(String userName, List<String> roleList) {
		super();
		this.userName = userName;
		this.roleList = roleList;
	}
	
	
}
