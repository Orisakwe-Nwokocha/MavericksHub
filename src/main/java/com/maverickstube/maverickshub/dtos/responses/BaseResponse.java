package com.maverickstube.maverickshub.dtos.responses;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse<T> {
    private int code;
    private boolean success;
    private T data;
}
