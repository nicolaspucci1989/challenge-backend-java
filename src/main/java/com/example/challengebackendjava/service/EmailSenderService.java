package com.example.challengebackendjava.service;

import com.example.challengebackendjava.event.UserCreatedEvent;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class EmailSenderService {

  public void sendEmail(UserCreatedEvent event) throws IOException {
    log.info("Email Sender, sending email...");

    final String sendgridEmail = System.getenv("SENDGRID_EMAIL");
    final String sendgridApiKey = System.getenv("SENDGRID_API_KEY");

    Email from = new Email(sendgridEmail);
    String subject = "Bienvendio";
    Email to = new Email(event.getUserEmail());
    Content content = new Content("text/plain", "La cuenta fue creada con exito");
    Mail mail = new Mail(from, subject, to, content);

    SendGrid sg = new SendGrid(sendgridApiKey);
    
    Request request = new Request();

    try {
      request.setMethod(Method.POST);
      request.setEndpoint("mail/send");
      request.setBody(mail.build());
      sg.api(request);
    } catch (IOException ex) { // TODO: fix
      throw ex;
    }
  }
}
