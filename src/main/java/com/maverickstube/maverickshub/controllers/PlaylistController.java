package com.maverickstube.maverickshub.controllers;

import com.maverickstube.maverickshub.dtos.requests.CreatePlaylistRequest;
import com.maverickstube.maverickshub.dtos.responses.CreatePlaylistResponse;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.services.PlaylistService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/api/v1/playlist")
@AllArgsConstructor
@Slf4j
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody CreatePlaylistRequest request)
            throws UserNotFoundException {
        log.info("Creating playlist: {}", request);
        System.out.println("de");
        CreatePlaylistResponse response = playlistService.create(request);
        log.info("Created playlist: {}", response);
        return ResponseEntity.status(CREATED).body(response);
    }
}
