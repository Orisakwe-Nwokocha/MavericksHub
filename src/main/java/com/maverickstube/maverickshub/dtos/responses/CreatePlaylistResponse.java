package com.maverickstube.maverickshub.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
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

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;

    private List<MediaResponse> media;

    private UserResponse uploader;

    private String message;
}
