package com.ingsoftware.contacts.services.implementations;

import javax.mail.MessagingException;

import com.ingsoftware.contacts.models.entities.User;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

  private final TemplateEngine templateEngine;

  private final JavaMailSender javaMailSender;

  public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
    this.templateEngine = templateEngine;
    this.javaMailSender = javaMailSender;
  }

  public String sendMail(User user) throws MessagingException, jakarta.mail.MessagingException {
    Context context = new Context();
    context.setVariable("user", user);

    String process = templateEngine.process("welcome", context);
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
    helper.setSubject("Welcome " + user.getFirstName());
    helper.setFrom("jankeza00@outlook.com");
    helper.setText(process, true);
    helper.setTo(user.getEmail());
    javaMailSender.send(mimeMessage);
    return "Sent";
  }
}
