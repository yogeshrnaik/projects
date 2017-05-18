package com.retail.store.controller.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    public String getMessage(String messageKey) {
        return msgSource.getMessage(messageKey, null, LocaleContextHolder.getLocale());
    }

    public ResponseEntity<ResponseDto> created(String msgKey, Long resourceId) {
        return new ResponseEntity<>(info(msgKey), getHeadersWithLocation(resourceId), HttpStatus.CREATED);
    }

    public ResponseEntity<ResponseDto> ok(String msgKey, Long resourceId) {
        return new ResponseEntity<>(info(msgKey), getHeadersWithLocation(resourceId), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> ok(String msgKey) {
        return new ResponseEntity<>(info(msgKey), getHeadersWithLocation(), HttpStatus.OK);
    }

    public <T> ResponseEntity<T> ok(T result) {
        return new ResponseEntity<>(result, getHeadersWithLocation(), HttpStatus.OK);
    }

    private HttpHeaders getHeadersWithLocation() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().build().toUri());
        return headers;
    }

    private HttpHeaders getHeadersWithLocation(Long id) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ServletUriComponentsBuilder.fromCurrentRequest().pathSegment("{id}")
            .buildAndExpand(id).toUri());
        return headers;
    }
}
