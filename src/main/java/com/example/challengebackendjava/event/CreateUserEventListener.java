package com.example.challengebackendjava.event;

import com.example.challengebackendjava.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateUserEventListener implements ApplicationListener<UserCreatedEvent> {
  private final EmailSenderService emailSenderService;

  @SneakyThrows
  @Override
  public void onApplicationEvent(UserCreatedEvent event) {
    log.info("User created event");
    log.info("User: {}", event.getUserEmail());
    emailSenderService.sendEmail(event);
  }
}
