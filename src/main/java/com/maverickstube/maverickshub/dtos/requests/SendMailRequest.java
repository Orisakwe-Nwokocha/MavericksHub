package com.maverickstube.maverickshub.dtos.requests;

import lombok.*;

@Getter
@Builder
public class SendMailRequest {
    private String recipientEmail;
    private String subject;
    private String recipientName;
    private String content;
}
