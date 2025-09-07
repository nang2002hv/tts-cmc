package com.quang.Identity_service.exception;

import com.quang.Identity_service.dto.request.ApiReponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiReponse> handlingRuntimeException(RuntimeException exception){
        ApiReponse apiReponse = new ApiReponse();

        apiReponse.setCode(ErorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiReponse.setMessage(ErorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);
    }
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiReponse> handlingAppException(AppException exception){
        ErorCode erorCode = exception.getErorCode();
        ApiReponse apiReponse = new ApiReponse();

        apiReponse.setCode(erorCode.getCode());
        apiReponse.setMessage(erorCode.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiReponse> handlingValidtion(MethodArgumentNotValidException exception){
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErorCode erorCode = ErorCode.INVALID_KEY;
        try {
            erorCode = ErorCode.valueOf(enumKey);
        } catch (IllegalArgumentException e){

        }
        ApiReponse apiReponse = new ApiReponse();

        apiReponse.setCode(erorCode.getCode());
        apiReponse.setMessage(erorCode.getMessage());


        return ResponseEntity.badRequest().body(apiReponse);
    }
}
