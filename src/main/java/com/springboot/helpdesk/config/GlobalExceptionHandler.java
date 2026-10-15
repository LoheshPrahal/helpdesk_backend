package com.springboot.helpdesk.config;

import com.springboot.helpdesk.Exception.OwnershipException;
import com.springboot.helpdesk.Exception.ResourceNotFoundException;
import com.springboot.helpdesk.dto.response.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException e){
        return ResponseEntity.badRequest().body(new ErrorDto(e.getMessage(), "ID not found in DB", Instant.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e
    ){
        Map<String, String> map = new HashMap<>();

        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> list = bindingResult.getFieldErrors();
        list.forEach(fieldError -> map.put(fieldError.getField(), fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest().body(map);
    }

    @ExceptionHandler(OwnershipException.class)
    public ResponseEntity<ErrorDto> handleOwnershipException(OwnershipException e){
        return ResponseEntity.badRequest().body(
                new ErrorDto(e.getMessage(), "Logged-in user cannot perform this operation", Instant.now())
        );
    }
}
