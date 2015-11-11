package com.spoton.esm.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandlingControllerAdvice {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlingControllerAdvice.class);

	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception e) throws Exception {
		logger.error("Request: " + req.getRequestURI() + " raised exception: ", e);

		ModelAndView mav = new ModelAndView("error");
		mav.addObject("name", e.getClass().getSimpleName());
		mav.addObject("url", req.getRequestURL());
		mav.addObject("timestamp", new Date().toString());
		mav.addObject("message", e.getMessage());
		mav.addObject("stack", getStackTrace(e));

		return mav;
	}

	private String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		return sw.toString();
	}
}