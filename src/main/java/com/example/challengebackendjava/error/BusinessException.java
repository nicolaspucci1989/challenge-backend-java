package com.example.challengebackendjava.error;

public class BusinessException extends RuntimeException{
  public BusinessException(String message) {
    super(message);
  }
}
