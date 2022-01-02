package com.example.challengebackendjava.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class UserCreatedEvent extends ApplicationEvent {
  private String msg;
  private String userEmail;

  public UserCreatedEvent(Object source, String msg, String userEmail) {
    super(source);
    this.msg = msg;
    this.userEmail = userEmail;
  }
}
