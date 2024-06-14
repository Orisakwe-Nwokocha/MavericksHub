package com.maverickstube.maverickshub.dtos.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class CreatePlaylistResponse {

    @JsonProperty("playlist_id")
    private Long id;
    @JsonProperty("playlist_name")
    private String name;
    @JsonProperty("playlist_description")
    private String description;

    @JsonFormat(pattern = "dd/MM/yyyy 'at' hh:mm a")
    private LocalDateTime timeCreated;

    @JsonFormat(pattern = "dd/MM/yyyy 'at' hh:mm a")
    private LocalDateTime timeUpdated;

    private List<MediaResponse> media = new ArrayList<>();

    private UserResponse uploader;

    private String message;
}
