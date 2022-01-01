package com.example.challengebackendjava.error;

public class BusinessException extends RuntimeException {
  public BusinessException(String msg) {
    super(msg);
  }
}
