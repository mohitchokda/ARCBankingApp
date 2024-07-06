package com.example.ARCBank.Dtos;

import java.util.List;

public class ErrorDto {
	
	List<String> errorsList;

	public ErrorDto() {
		// TODO Auto-generated constructor stub
	}
	
	public ErrorDto(List<String> msgs) {
		errorsList = msgs;
	}
	
	public List<String> getErrorMsg() {
		return errorsList;
	}

	public void setErrorMsg(List<String> errors) {
		this.errorsList = errors;
	}

}
