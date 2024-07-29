package com.prunnytest.bookstore.exception;


import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handle(NotFoundException nfe){
      return ResponseEntity
              .status(HttpStatus.NOT_FOUND)
              .body(nfe.getMessage());
  }

  @ExceptionHandler(NotAccessibleException.class)
  public ResponseEntity<String> handle(NotAccessibleException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<CustomErrorResponse> handle(MethodArgumentNotValidException manve){
    var errors =new HashMap<String, String>();
    manve.getAllErrors()
            .forEach(error -> {
                var title = ((FieldError)error).getField();
                var body = error.getDefaultMessage();
                errors.put(title, body);
            });
    return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new CustomErrorResponse(errors));
  }




//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<CustomErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
//        String errorMessage = ex.getBindingResult().getAllErrors().stream()
//                .map(ObjectError::getDefaultMessage)
//                .collect(Collectors.joining(", "));
//        CustomErrorResponse response = new CustomErrorResponse(errorMessage, HttpStatus.BAD_REQUEST.value());
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<CustomErrorResponse> handleGenericException(Exception ex) {
//        CustomErrorResponse response = new CustomErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomErrorResponse> handleGenericException(Exception ex) {
        var errors = new HashMap<String, String>();
        errors.put(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex.getMessage());
        CustomErrorResponse response = new CustomErrorResponse(errors);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }

}
