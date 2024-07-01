package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.config.MailConfig;
import com.maverickstube.maverickshub.dtos.requests.BrevoMailRequest;
import com.maverickstube.maverickshub.dtos.requests.Recipient;
import com.maverickstube.maverickshub.dtos.requests.SendMailRequest;
import com.maverickstube.maverickshub.dtos.requests.Sender;
import com.maverickstube.maverickshub.dtos.responses.BrevoMailResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpStatusCode.valueOf;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@AllArgsConstructor
public class MavericksHubMailService implements MailService {
    private final MailConfig mailConfig;

    @Override
    public String sendMail(SendMailRequest mailRequest) {
        RestTemplate restTemplate = new RestTemplate();
        String url = mailConfig.getMailApiUrl();
        BrevoMailRequest request = BrevoMailRequest.builder()
                .subject(mailRequest.getSubject())
                .sender(new Sender())
                .recipients(
                        List.of(new Recipient(mailRequest.getRecipientName(),
                                mailRequest.getRecipientEmail())))
                .content(mailRequest.getContent())
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.set("api-key", mailConfig.getMailApiKey());
        headers.set("accept", APPLICATION_JSON.toString());
        RequestEntity<?> httpRequest = new RequestEntity<>(request, headers, POST, URI.create(url));
        ResponseEntity<BrevoMailResponse> response = restTemplate.postForEntity(url, httpRequest, BrevoMailResponse.class);
        boolean isSuccessful = response.getBody() != null && response.getStatusCode() == valueOf(201);
        if (isSuccessful) return "Mail sent successfully";
        throw new RuntimeException("Email sending failed");
    }
}
