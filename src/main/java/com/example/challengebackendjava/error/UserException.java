package com.example.challengebackendjava.error;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_REQUEST)
public class UserException extends RuntimeException {
  public UserException(String message) {
    super(message);
  }
}
