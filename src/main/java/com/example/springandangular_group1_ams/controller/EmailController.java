package com.example.springandangular_group1_ams.controller;

import com.example.springandangular_group1_ams.model.entity.Email;
import com.example.springandangular_group1_ams.service.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/")
    public String sendMail(@RequestBody Email email) {
          return emailService.sendMail(email);
    }
}
