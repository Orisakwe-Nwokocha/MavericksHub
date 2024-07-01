package com.maverickstube.maverickshub.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverickstube.maverickshub.dtos.requests.LoginRequest;
import com.maverickstube.maverickshub.dtos.responses.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.maverickstube.maverickshub.utils.TestUtils.TEST_IMAGE_LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
public class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testUploadMedia() throws Exception {
        try(InputStream inputStream= Files.newInputStream(Path.of(TEST_IMAGE_LOCATION))) {
            MultipartFile file = new MockMultipartFile("mediaFile", inputStream);
            mockMvc.perform(multipart("/api/v1/media")
                            .file(file.getName(), file.getBytes())
                            .part(new MockPart("userId", "200".getBytes()))
                            .part(new MockPart("description", "test description".getBytes()))
                            .part(new MockPart("category", "ACTION".getBytes()))
                            .contentType(MediaType.MULTIPART_FORM_DATA))
                            .andExpect(status().isCreated())
                            .andDo(print());
        }catch (Exception exception){
            throw new RuntimeException(exception);
        }

    }

    @Test
    public void testGetMediaForUser() throws Exception {
//        String token = getToken();
        mockMvc.perform(get("/api/v1/media?userId=200")
//                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());

    }

    public String getToken() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("john@email.com");
        request.setPassword("password");
        ObjectMapper mapper = new ObjectMapper();
        MvcResult result = mockMvc.perform(post("/api/v1/auth")
                        .contentType(APPLICATION_JSON)
                        .content(mapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andReturn();
        LoginResponse response = mapper.readValue(result.getResponse().getContentAsString(), LoginResponse.class);
        System.out.println("RESULT: "+ response.getToken());
        return  response.getToken();
    }


    @Test
    public void testGetMediaForUserShouldFailForInvalidUserId() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/media?userId=20000")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andDo(print());


    }
}
