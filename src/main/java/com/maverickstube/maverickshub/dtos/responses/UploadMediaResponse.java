package com.maverickstube.maverickshub.dtos.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maverickstube.maverickshub.data.models.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadMediaResponse {
    private Long id;
    @JsonProperty("media_url")
    private String url;
    @JsonProperty("media_description")
    private String description;
    private Category category;
}
