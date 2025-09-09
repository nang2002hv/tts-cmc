package com.quang.Identity_service.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErorCode {
    UNCATEGORIZED_EXCEPTION(9999,"UNCATEGORIZED EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY(1001,"Invalid String", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002,"User existed",HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1003,"user name must be at least 3 character",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1004,"user name must be at least 8 character",HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1005,"User not existed",HttpStatus.NOT_FOUND),//404
    UNAUTHENTICATED(1006,"UNAUTHENTICATED",HttpStatus.UNAUTHORIZED),//401
    UNAUTHORIZED(1007,"you do not have permission",HttpStatus.FORBIDDEN),
    INVALID_DOB(1007,"invalid DOB",HttpStatus.BAD_REQUEST),
    ;
    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErorCode(int code, String message,HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

}
