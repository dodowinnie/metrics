package com.brandon.metrics.gateway.dto;

public class ReturnData<T> {

	private int code;

    private String mass;

    private T data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMass() {
		return mass;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void setMass(String mass) {
		this.mass = mass;
	}

	public ReturnData(int code, String mass, T data) {
		super();
		this.code = code;
		this.mass = mass;
		this.data = data;
	}

	

	
}
