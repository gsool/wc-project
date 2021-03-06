package com.nicchagil.util.exception.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicchagil.util.exception.BusinessException;
import com.nicchagil.util.exception.ExceptionCodeEnum;
import com.nicchagil.util.exception.StandardResponse;

@RestController
@RequestMapping("/exception")
public class ExceptionController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 异常抛出Mock接口
	 */
	@GetMapping("/exceptionMock") // http://127.0.0.1/i18n/exceptionMock?exceptionClassName=exceptionClassName
	public StandardResponse<String> exceptionMock(HttpServletRequest request, String exceptionClassName) {
		switch (exceptionClassName) {
		case "BusinessException":
			throw new BusinessException(ExceptionCodeEnum.MSG_00001);
		case "IllegalArgumentException":
			throw new IllegalArgumentException();
		case "NoException":
			return StandardResponse.getSuccessResponse("OK");
		default:
			break;
		}
		
		return StandardResponse.getSuccessResponse("OK");
	}
	
}
