package com.example.challengebackendjava.event;

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
public class EmailSender {
  public void sendEmail(UserCreatedEvent event) throws IOException {
    log.info("Email Sender, sending email...");

    Email from = new Email(System.getenv("SENDGRID_EMAIL"));
    String subject = "Bienvendio";
    Email to = new Email(event.getUserEmail());
    Content content = new Content("text/plain", "La cuenta fue creada con exito");
    Mail mail = new Mail(from, subject, to, content);
    final String sendgrid_api_key = System.getenv("SENDGRID_API_KEY");

    SendGrid sg = new SendGrid(sendgrid_api_key);
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
