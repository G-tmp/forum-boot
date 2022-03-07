package com.gtmp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonRes {

    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = 1;
    public static final String SUCCESS_MSG = "succeeded";
    public static final String ERROR_MSG = "failed";

    private int code;
    private String msg;
    private Object data;


    public JsonRes(){
    }

    public int getCode() {
        return code;
    }

    public JsonRes setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonRes setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public JsonRes setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "JsonRes{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

}
