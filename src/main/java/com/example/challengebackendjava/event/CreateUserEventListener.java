package com.example.challengebackendjava.event;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateUserEventListener implements ApplicationListener<UserCreatedEvent> {
  private final EmailSender emailSender;

  @SneakyThrows
  @Override
  public void onApplicationEvent(UserCreatedEvent event) {
    log.info("User created event");
    log.info("User: {}", event.getUserEmail());
    emailSender.sendEmail(event);
  }
}
