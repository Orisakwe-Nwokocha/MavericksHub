package com.maverickstube.maverickshub.dtos.requests;

import com.maverickstube.maverickshub.data.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateMediaRequest {
    private String description;
    private Category category;
}
