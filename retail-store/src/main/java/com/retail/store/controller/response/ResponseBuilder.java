package com.retail.store.controller.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.retail.store.dto.ResponseDto;
import com.retail.store.dto.ResponseType;

@Component
public class ResponseBuilder {

    @Autowired
    private MessageSource msgSource;

    public ResponseDto info(String messageKey) {
        return new ResponseDto(ResponseType.INFO, getMessage(messageKey));
    }

    public ResponseDto error(String messageKey) {
        return new ResponseDto(ResponseType.ERROR, getMessage(messageKey));
    }

    private String getMessage(String messageKey) {
        return msgSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }

}
