package com.ron.FoodDelivery.mail;

import com.ron.FoodDelivery.entities.user.UserEntity;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;

@Service
public class MailServiceImpl extends AbstractMailTemplateDefault implements MailService {
    @Autowired
    private TemplateEngine templateEngine;
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String USERNAME_APP;
    private static final String ENCODING = "UTF-8";

    @Override
    public void send_otp(UserEntity user, String otp) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, ENCODING);
            mimeMessageHelper.setSubject("Authentication OTP");
            mimeMessageHelper.setFrom(USERNAME_APP);
            mimeMessageHelper.setTo(user.getEmail());

            Context context = new Context();
            context.setVariable("otp", otp);
            context.setVariable("name", user.getLast_name());
            context.setVariable("time_sent", new Date());
            String content = templateEngine.process(OTP_TEMPLATE, context);
            mimeMessageHelper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException exception) {
            exception.getCause();
        }
    }
}
