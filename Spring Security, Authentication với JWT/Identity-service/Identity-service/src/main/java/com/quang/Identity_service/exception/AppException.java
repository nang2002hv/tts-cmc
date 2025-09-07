package com.quang.Identity_service.exception;

public class AppException extends RuntimeException {
    public AppException(ErorCode erorCode) {
        super(erorCode.getMessage());
        this.erorCode = erorCode;
    }

    private ErorCode erorCode;

    public ErorCode getErorCode() {
        return erorCode;
    }

    public void setErorCode(ErorCode erorCode) {
        this.erorCode = erorCode;
    }
}
