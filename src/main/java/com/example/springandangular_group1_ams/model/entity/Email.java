package com.example.springandangular_group1_ams.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Email {
    private String toEmail;
    private String senderName;
    private String subject;
    private String body;
}
