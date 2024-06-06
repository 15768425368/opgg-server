package com.zhang.storagestatisticsserve.config;

import lombok.Data;

import java.util.HashMap;

@Data
public final class ApiResponse {

    private Integer code;
    private String message;
    private Object data;

    public ApiResponse(int code, String errorMsg, Object data) {
        this.code = code;
        this.message = errorMsg;
        this.data = data;
    }

    public static ApiResponse ok() {
        return new ApiResponse(200, "", new HashMap<>());
    }

    public static ApiResponse ok(Object data) {
        return new ApiResponse(200, "success", data);
    }

    public static ApiResponse error(String errorMsg) {
        return new ApiResponse(500, errorMsg, new HashMap<>());
    }
}
