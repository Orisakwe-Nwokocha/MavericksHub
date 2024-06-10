package com.maverickstube.maverickshub.dtos.requests;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePlaylistRequest {
    private String name;
    private String description;
    private Long userId;
}
