package com.clarauso.demo.model.dto;

public record ErrorModel(ErrorCode code, String message) {

  public enum ErrorCode {
    GEN_ERR,
    NOT_FOUND,
    BAD_REQUEST
  }
}
