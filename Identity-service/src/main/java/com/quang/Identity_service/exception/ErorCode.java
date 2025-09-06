package com.quang.Identity_service.exception;

public enum ErorCode {
    INVALID_KEY(1001,"Invalid String"),
    UNCATEGORIZED_EXCEPTION(9999,"UNCATEGORIZED EXCEPTION"),
    USER_EXISTED(1002,"User existed"),
    USERNAME_INVALID(1003,"user name must be at least 3 character"),
    INVALID_PASSWORD(1004,"user name must be at least 8 character")
    ;
    private int code;
    private String message;

    ErorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
