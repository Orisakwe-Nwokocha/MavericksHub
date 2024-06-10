package com.maverickstube.maverickshub.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ShufflePlaylistResponse {
    @JsonProperty("playlist_id")
    private Long id;
    @JsonProperty("playlist_name")
    private String name;
    @JsonProperty("playlist_description")
    private String description;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeCreated;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeUpdated;

    private List<MediaResponse> media;

    private UserResponse uploader;

}
