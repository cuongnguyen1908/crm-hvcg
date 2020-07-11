package com.hvcg.api.crm.dto;


import org.springframework.stereotype.Component;

@Component
public class ResponseDTO<T> {

    private T content;
    private String message;

    public ResponseDTO() {
    }

    public ResponseDTO(T content, String message) {
        this.content = content;
        this.message = message;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
