package com.digitalhouse.digitalexpirience.service.impl;


import com.digitalhouse.digitalexpirience.mail.MailRegisterUser;
import com.digitalhouse.digitalexpirience.mail.MailType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Map;


@Service
public class MailServiceImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);


    @Autowired
    private JavaMailSender mailSender;

    @Value("${send.mail.from}")
    private String mailFrom;

    @Autowired
    private ObjectFactory<MailRegisterUser> mailRegisterObjectFactory;


    /**
     * Envia un mail.
     */

    public void sendMail(MailType mail, Map<String, Object> model, String destinatario) {

        if (destinatario.isEmpty()) {
            LOGGER.warn("sendMails - destinatario vacío para mail: {}", mail.getAsunto());
        }

        String asunto = mail.getAsunto();
        String html = mail.getHtml(model);

        enviarSmtp(destinatario, asunto, html);

    }

    @Async
    private void enviarSmtp(String destinatario, String asunto, String html) {

        MimeMessage message = mailSender.createMimeMessage();

        try {
            message.setSubject(asunto);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(destinatario);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }


    public MailRegisterUser createMailRegister() {
        MailRegisterUser mailRegister = mailRegisterObjectFactory.getObject();
        return mailRegister;
    }

//    public MailWelcomeUser mailWelcomeUser() {
//        return mailWelcomeUser = mailWelcomeUserObjectFactory.getObject();
//
//    }


}
