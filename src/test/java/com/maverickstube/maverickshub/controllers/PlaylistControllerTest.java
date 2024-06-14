package com.maverickstube.maverickshub.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maverickstube.maverickshub.dtos.requests.CreatePlaylistRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = {"/db/data.sql"})
public class PlaylistControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void playlistWhenCreatedThenCreated() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        CreatePlaylistRequest request = new CreatePlaylistRequest();
        request.setName("name");
        request.setDescription("description");
        request.setUserId(200L);
        mvc.perform(post("/api/v1/playlist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isCreated())
                .andDo(print());

    }
}
