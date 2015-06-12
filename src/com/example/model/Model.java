package com.example.model;

import java.util.List;
import java.util.Map;


public class Model {

	private String yearStr;
	private List<Map<String, String>> data;
	
	public Model() {
		super();
	}

	
	

	public List<Map<String, String>> getData() {
		return data;
	}




	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}




	public String getYearStr() {
		return yearStr;
	}


	public void setYearStr(String yearStr) {
		this.yearStr = yearStr;
	}

	
	
}
