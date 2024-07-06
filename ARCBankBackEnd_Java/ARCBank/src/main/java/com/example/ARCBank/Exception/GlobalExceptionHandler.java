package com.example.ARCBank.Exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.ARCBank.Dtos.ErrorDto;

import lombok.val;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(value = {Exception.class})
	public ResponseEntity<String> handleAnyException(Exception ex,WebRequest webRequest){
		System.out.println("Handling Exception");
		ResponseEntity<String> res = new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		return res;
	}
	
	@ExceptionHandler(value = {BadCredentialsException.class})
	public ResponseEntity<String> handleBadCredntials(BadCredentialsException ex,WebRequest webRequest){
		System.out.println("Handling CREDS exception");
		ResponseEntity<String> res = new ResponseEntity<>(ex.getMessage(),HttpStatus.UNAUTHORIZED);
		return res;
	}

	
	//@ExceptionHandler(MethodArgumentNotValidException.class)
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		
		List<String> errors = new ArrayList<>();
		
		for(ObjectError e : ex.getBindingResult().getAllErrors()) {
			errors.add(e.getDefaultMessage());
		}
		
		//ErrorDto error= new ErrorDto("Invalid Error");
		ErrorDto error = new ErrorDto(errors); 
		
		ResponseEntity<Object> res = new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
		
		return res;
	}

}
