package com.retail.store.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.retail.store.controller.response.ResponseBuilder;
import com.retail.store.dto.ResponseDto;
import com.retail.store.exception.NotFoundException;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {

    @Autowired
    private ResponseBuilder response;

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    public ResponseEntity<ResponseDto> processValidationError(NotFoundException ex) {
        return new ResponseEntity<>(response.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
