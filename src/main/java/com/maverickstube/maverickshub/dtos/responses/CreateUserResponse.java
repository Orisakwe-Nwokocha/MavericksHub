package com.maverickstube.maverickshub.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserResponse {
    private Long id;
    private String email;
    private String message;
}
