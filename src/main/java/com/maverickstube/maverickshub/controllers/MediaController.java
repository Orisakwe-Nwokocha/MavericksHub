package com.maverickstube.maverickshub.controllers;

import com.maverickstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.services.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/media")
@AllArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<UploadMediaResponse> uploadMedia(@ModelAttribute UploadMediaRequest uploadMediaRequest){
        return ResponseEntity.status(CREATED)
                .body(mediaService.upload(uploadMediaRequest));
    }


    @GetMapping
    public ResponseEntity<?> getMediaForUser(@RequestParam Long userId, Authentication authentication, Principal principal) throws UserNotFoundException {
        System.out.println("reached here: " + authentication.getAuthorities());
        System.out.println("name auth: " + authentication.getName());
        System.out.println("principal auth: " + authentication.getPrincipal());
        System.out.println("credentials: " + authentication.getCredentials());
        System.out.println("principal: " + principal.getName());
        return ResponseEntity.ok(mediaService.getMediaFor(userId));
    }

}
