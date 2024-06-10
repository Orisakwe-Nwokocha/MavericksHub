package com.maverickstube.maverickshub.dtos.requests;

import com.maverickstube.maverickshub.data.models.Category;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class UploadMediaRequest {
    private Long userId;
    private MultipartFile mediaFile;
    private String description;
    private Category category;
}
