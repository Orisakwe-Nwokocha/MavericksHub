package com.maverickstube.maverickshub.services;


import com.maverickstube.maverickshub.dtos.requests.SendMailRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@Sql("/db/data.sql")
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSendEmail(){
        String email = "niyaye8317@nolanzip.com";
        SendMailRequest mailRequest = SendMailRequest.builder()
                .recipientEmail(email)
                .subject("Hello")
                .recipientName("John DOe")
                .content("<p>Hello from the other side</p>")
                .build();
        String response = mailService.sendMail(mailRequest);

        assertThat(response).isNotNull();
        assertThat(response).containsIgnoringCase("success");
    }
}
