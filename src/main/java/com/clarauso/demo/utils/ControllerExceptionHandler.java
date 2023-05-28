package com.clarauso.demo.utils;

import com.clarauso.demo.model.exceptions.InvalidInputException;
import com.clarauso.demo.model.exceptions.NotFoundException;
import com.clarauso.demo.model.dto.ErrorModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.clarauso.demo.model.dto.ErrorModel.*;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorModel> handleException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(new ErrorModel(ErrorCode.GEN_ERR, "An error occurred"));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorModel> handleNotFoundException(NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorModel(ErrorCode.NOT_FOUND, "Resource not found"));
  }

  @ExceptionHandler(InvalidInputException.class)
  public ResponseEntity<ErrorModel> handleInvalidInputException(InvalidInputException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(new ErrorModel(ErrorCode.BAD_REQUEST, "Invalid input"));
  }
}
