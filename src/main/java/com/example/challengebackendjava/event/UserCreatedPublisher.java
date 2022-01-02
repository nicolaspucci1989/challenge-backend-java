package com.example.challengebackendjava.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserCreatedPublisher {
  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  public void publishUserCreatedEvent(final String message, final String userEmail) {
    log.info("Publishing user created event {}", message);
    UserCreatedEvent userCreatedEvent = new UserCreatedEvent(this, message, userEmail);
    applicationEventPublisher.publishEvent(userCreatedEvent);
  }
}
