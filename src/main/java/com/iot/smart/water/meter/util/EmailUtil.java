package com.iot.smart.water.meter.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;

    public void postEmail(String name, String contact, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("250714751@qq.com");
        message.setTo(contact);
        message.setSubject(name);
        message.setText(contact);
        mailSender.send(message);
    }
}
