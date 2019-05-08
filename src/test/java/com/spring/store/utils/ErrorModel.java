package com.spring.store.utils;

import java.util.Map;

import lombok.Data;

@Data
public class ErrorModel {

    private String error;

    private String errorCode;

    private String uuid;

    private Map<String, String> fieldErrors;

}
