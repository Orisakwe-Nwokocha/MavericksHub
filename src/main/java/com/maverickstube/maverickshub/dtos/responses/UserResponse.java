package com.maverickstube.maverickshub.dtos.responses;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.Column;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class UserResponse {
    private Long id;
    private String email;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeCreated;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime timeUpdated;
}
