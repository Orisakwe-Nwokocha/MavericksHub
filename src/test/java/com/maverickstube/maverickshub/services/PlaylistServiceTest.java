package com.maverickstube.maverickshub.services;


import com.maverickstube.maverickshub.data.models.Media;
import com.maverickstube.maverickshub.data.models.Playlist;
import com.maverickstube.maverickshub.dtos.requests.AddMediaToPlaylistRequest;
import com.maverickstube.maverickshub.dtos.requests.CreatePlaylistRequest;
import com.maverickstube.maverickshub.dtos.requests.ShufflePlaylistRequest;
import com.maverickstube.maverickshub.dtos.responses.MediaResponse;
import com.maverickstube.maverickshub.exceptions.PlaylistNotFoundException;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class PlaylistServiceTest {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testCreatePlaylist() throws UserNotFoundException, PlaylistNotFoundException {
        CreatePlaylistRequest request = new CreatePlaylistRequest();
        request.setName("name");
        request.setDescription("description");
        request.setUserId(200L);
        var response = playlistService.create(request);
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).contains("success");
    }


    @Test
    public void addMediaToPlaylist() throws PlaylistNotFoundException {
        AddMediaToPlaylistRequest addMediaRequest = new AddMediaToPlaylistRequest();
        addMediaRequest.setPlaylistId(300L);
        addMediaRequest.setMediaId(100L);
        var response = playlistService.addMediaToPlaylist(addMediaRequest);
        System.out.println(response);
        assertThat(response).isNotNull();
        assertThat(response.getMedia().size()).isEqualTo(3);

    }

    @Test
    public void shufflePlaylist() throws PlaylistNotFoundException {
        ShufflePlaylistRequest request = new ShufflePlaylistRequest();
        request.setPlaylistId(300L);
        List<Media> media1 = playlistService.getAllMedia(300L);
        var response = playlistService.shuffle(request);
        List<MediaResponse> media = response.getMedia();
        List<Media> media2 = media.stream()
                .map(item->modelMapper.map(item, Media.class))
                .toList();
        System.out.println("Media 1: "+media1);
        System.out.println("Media 2: "+media2);
        assertNotEquals(media1, media2);
    }

}
