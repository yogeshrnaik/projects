package com.retail.store.dto;

public class ResponseDto {

    private ResponseType type;
    private String message;

    public ResponseDto(ResponseType type, String message) {
        this.message = message;
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }
}
