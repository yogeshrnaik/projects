package com.retail.store.controller.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.retail.store.controller.response.ResponseBuilder;
import com.retail.store.dto.ResponseDto;
import com.retail.store.exception.RetailStoreException;

@ControllerAdvice
public class RetailStoreExceptionHandler {

    @Autowired
    private ResponseBuilder response;

    @ExceptionHandler(RetailStoreException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseDto processValidationError(RetailStoreException ex) {
        return response.error(ex.getMessage());
    }
}
