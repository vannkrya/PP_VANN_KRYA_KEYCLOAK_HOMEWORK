package com.example.springandangular_group1_ams.service.serviceImpl;

import com.example.springandangular_group1_ams.exception.NullExceptionClass;
import com.example.springandangular_group1_ams.model.entity.Email;
import com.example.springandangular_group1_ams.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImp implements EmailService {
    private final JavaMailSender mailSender;

    public EmailServiceImp(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public String sendMail(Email email) {
        if(email.getToEmail().isEmpty() || email.getSubject().isEmpty() || email.getSenderName().isEmpty() || email.getBody().isEmpty()){
            throw new NullExceptionClass("All Fields is required!","Not Null Field");
        }
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            String html = "<!doctype html>\n" +
                    "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:th=\"http://www.thymeleaf.org\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "    content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Email</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p>" + email.getBody()+ "</p>\n\n" +
                    "<div>Best regards,</div>" +
                    "<div> " + email.getSenderName() + "</div>" +
                    "</body>\n" +
                    "</html>\n";

            helper.setTo(email.getToEmail());
            helper.setSubject(email.getSubject());
            helper.setText(html,true);
            mailSender.send(message);
            return "email send success..!!";
        }catch (Exception e){
            return "email fail to send";
        }
    }
}
