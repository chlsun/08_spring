package com.kh.spring.exception.controller;



import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.kh.spring.exception.AuthenticationException;
import com.kh.spring.exception.DuplicateIdException;
import com.kh.spring.exception.InvalidParameterException;
import com.kh.spring.exception.MemberNotFoundException;
import com.kh.spring.exception.PasswordNotMatchException;
import com.kh.spring.exception.TooLageValueException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ExceptionHandlingController {
	
	private ModelAndView createErrorResponse(String errorMsg, Exception e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("message", e.getMessage())
			.setViewName("include/error_page");
		return mv;
	}
	
	@ExceptionHandler(InvalidParameterException.class)
	protected ModelAndView invalidParameterError(RuntimeException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(TooLageValueException.class)
	protected ModelAndView tooLageValueError(RuntimeException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(MemberNotFoundException.class)
	protected ModelAndView memberNotFoundError(RuntimeException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(PasswordNotMatchException.class)
	protected ModelAndView passwordNotMatchError(RuntimeException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(DuplicateIdException.class)
	protected ModelAndView duplicateIdError(RuntimeException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	protected ModelAndView authenticationError(RuntimeException e) {
		return createErrorResponse(e.getMessage(), e);
	}
	
	
//	@ExceptionHandler(TooLageValueException.class)
//	protected ModelAndView tooLageValueError(TooLageValueException e) {
//		
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("message", e.getMessage())
//			.setViewName("include/error_page");
//		return mv;
//	}
//	
//	@ExceptionHandler(InvalidParameterException.class)
//	protected ModelAndView invalidParameterError(InvalidParameterException e) {
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("message", e.getMessage())
//			.setViewName("include/error_page");
//		return mv;
//	}
	
}
