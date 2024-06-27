package com.maverickstube.maverickshub.services;


import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jackson.jsonpointer.JsonPointerException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchOperation;
import com.github.fge.jsonpatch.ReplaceOperation;
import com.maverickstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.responses.MediaResponse;
import com.maverickstube.maverickshub.dtos.responses.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.data.models.Category;
import com.maverickstube.maverickshub.data.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.maverickstube.maverickshub.data.models.Category.STEP_MOM;
import static com.maverickstube.maverickshub.utils.TestUtils.TEST_VIDEO_LOCATION;
import static com.maverickstube.maverickshub.utils.TestUtils.buildUploadMediaRequest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
@Sql(scripts = {"/db/data.sql"})
public class MediaServiceTest {

    @Autowired
    private MediaService mediaService;

    @Test
    public void uploadMediaTest(){
        Path path = Paths.get(TEST_VIDEO_LOCATION);
        try(var inputStream = Files.newInputStream(path)) {
            UploadMediaRequest request = buildUploadMediaRequest(inputStream);
            UploadMediaResponse response = mediaService.upload(request);
            log.info("response--> {}", response);
            assertThat(response).isNotNull();
            assertThat(response.getUrl()).isNotNull();
        }catch (IOException exception){
            assertThat(exception).isNull();
        }
    }


    @Test
    public void getMediaByIdTest(){
        Media media = mediaService.getMediaBy(100L);
        log.info("found content--> {}", media);
        assertThat(media).isNotNull();
    }

    @Test
    public void getMediaForUserTest() throws UserNotFoundException {
        Long userId = 200L;
        List<MediaResponse> media = mediaService.getMediaFor(userId);
        log.info("media items--> {}", media);
        assertThat(media).hasSize(3);
    }

    @Test
    @DisplayName("test update media files")
    public void updateMediaTest() throws JsonPointerException {
        Category category  =  mediaService.getMediaBy(103L).getCategory();
        assertThat(category).isNotEqualTo(STEP_MOM);

        List<JsonPatchOperation> operations = List.of(
                new ReplaceOperation(new JsonPointer("/category"),
                        new TextNode(STEP_MOM.name()))
        );
        JsonPatch updateMediaRequest = new JsonPatch(operations);
        UpdateMediaResponse response = mediaService.updateMedia(103L,
                updateMediaRequest);
        assertThat(response).isNotNull();
        category  =  mediaService.getMediaBy(103L).getCategory();
        assertThat(category).isEqualTo(STEP_MOM);
    }
}
