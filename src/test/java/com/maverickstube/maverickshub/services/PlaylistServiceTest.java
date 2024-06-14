package com.maverickstube.maverickshub.services;


import com.maverickstube.maverickshub.data.models.Media;
import com.maverickstube.maverickshub.dtos.requests.AddMediaToPlaylistRequest;
import com.maverickstube.maverickshub.dtos.requests.CreatePlaylistRequest;
import com.maverickstube.maverickshub.dtos.requests.ShufflePlaylistRequest;
import com.maverickstube.maverickshub.dtos.responses.MediaResponse;
import com.maverickstube.maverickshub.exceptions.PlaylistNotFoundException;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
public class PlaylistServiceTest {

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private MediaService mediaService;


    @Test
    public void testCreatePlaylist() throws UserNotFoundException {
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
        addMediaRequest.setMediaId(102L);
        Media media = mediaService.getMediaBy(102L);
        System.out.println("before: "+media.getPlaylist().size());

        var response = playlistService.addMediaToPlaylist(addMediaRequest);
        System.out.println(response);
        media = mediaService.getMediaBy(102L);
        System.out.println("after: "+media.getPlaylist().size());

        assertThat(response).isNotNull();
        assertThat(response.getMedia().size()).isEqualTo(4);
    }

    @Test
    public void playlistWhenSameMediaIsAddedThenPlaylistRemainsUnchanged() throws PlaylistNotFoundException {
        AddMediaToPlaylistRequest addMediaRequest = new AddMediaToPlaylistRequest();
        Long playlistId = 300L;
        addMediaRequest.setPlaylistId(playlistId);
        Long mediaId = 101L;
        addMediaRequest.setMediaId(mediaId);

        Set<Media> media = playlistService.getPlaylistBy(playlistId).getMedia();
        assertThat(media).isNotNull();
        var filteredMediaList = media.stream().filter(m -> m.getId().equals(mediaId)).toList();
        assertThat(media.size()).isEqualTo(3);
        assertThat(filteredMediaList.size()).isEqualTo(1);

        var response = playlistService.addMediaToPlaylist(addMediaRequest);
        assertThat(response).isNotNull();
        media = playlistService.getPlaylistBy(playlistId).getMedia();
        assertThat(media).isNotNull();
        filteredMediaList = media.stream().filter(m -> m.getId().equals(mediaId)).toList();
        assertThat(media.size()).isEqualTo(3);
        assertThat(filteredMediaList.size()).isEqualTo(1);
    }

    @Test
    public void shufflePlaylistTest() throws PlaylistNotFoundException {
        Long playlistId = 300L;
        ShufflePlaylistRequest request = new ShufflePlaylistRequest();
        request.setPlaylistId(playlistId);
        var response = playlistService.shuffle(request);

        List<MediaResponse> originalMediaList = playlistService.getAllMedia(playlistId);
        List<MediaResponse> shuffledMediaList = response.getMedia();
        System.out.println("Original Media List: " + originalMediaList);
        System.out.println("Shuffled Media List: " + shuffledMediaList);

        assertNotEquals(originalMediaList, shuffledMediaList, "The shuffled media list should not be the same as the original media list.");
    }


}
