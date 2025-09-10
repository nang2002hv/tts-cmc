package com.quang.Identity_service.exception;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.quang.Identity_service.dto.request.ApiReponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String MIN_ATTRIBUTES = "min";

    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiReponse> handlingRuntimeException(RuntimeException exception) {
        ApiReponse apiReponse = new ApiReponse();

        apiReponse.setCode(ErorCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiReponse.setMessage(ErorCode.UNCATEGORIZED_EXCEPTION.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiReponse> handlingAppException(AppException exception) {
        ErorCode erorCode = exception.getErorCode();
        ApiReponse apiReponse = new ApiReponse();

        apiReponse.setCode(erorCode.getCode());
        apiReponse.setMessage(erorCode.getMessage());

        return ResponseEntity.status(erorCode.getStatusCode()).body(apiReponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiReponse> handlingAccessDeniedException(AccessDeniedException exception) {
        ErorCode erorCode = ErorCode.UNAUTHORIZED;
        return ResponseEntity.status(erorCode.getStatusCode())
                .body(ApiReponse.builder()
                        .code(erorCode.getCode())
                        .message(erorCode.getMessage())
                        .build());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiReponse> handlingValidtion(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();

        ErorCode erorCode = ErorCode.INVALID_KEY;
        Map<String, Object> attributes = null;
        try {
            erorCode = ErorCode.valueOf(enumKey);

            var constraintViolation =
                    exception.getBindingResult().getAllErrors().getFirst().unwrap(ConstraintViolation.class);

            attributes = constraintViolation.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());
        } catch (IllegalArgumentException e) {

        }
        ApiReponse apiReponse = new ApiReponse();

        apiReponse.setCode(erorCode.getCode());
        apiReponse.setMessage(
                Objects.nonNull(attributes) ? mapAtrribute(erorCode.getMessage(), attributes) : erorCode.getMessage());

        return ResponseEntity.badRequest().body(apiReponse);
    }

    private String mapAtrribute(String message, Map<String, Object> attributes) {
        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTES));
        return message.replace("{" + MIN_ATTRIBUTES + "}", minValue);
    }
}
