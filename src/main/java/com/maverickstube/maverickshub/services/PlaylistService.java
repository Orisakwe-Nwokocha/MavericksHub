package com.maverickstube.maverickshub.services;


import com.maverickstube.maverickshub.data.models.Media;
import com.maverickstube.maverickshub.data.models.Playlist;
import com.maverickstube.maverickshub.dtos.requests.AddMediaToPlaylistRequest;
import com.maverickstube.maverickshub.dtos.requests.CreatePlaylistRequest;
import com.maverickstube.maverickshub.dtos.requests.ShufflePlaylistRequest;
import com.maverickstube.maverickshub.dtos.responses.AddMediaToPlaylistResponse;
import com.maverickstube.maverickshub.dtos.responses.CreatePlaylistResponse;
import com.maverickstube.maverickshub.dtos.responses.ShufflePlaylistResponse;
import com.maverickstube.maverickshub.exceptions.PlaylistNotFoundException;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PlaylistService {
    CreatePlaylistResponse create(CreatePlaylistRequest request) throws UserNotFoundException;

    Playlist getPlaylistBy(Long id) throws PlaylistNotFoundException;

     AddMediaToPlaylistResponse addMediaToPlaylist(AddMediaToPlaylistRequest addMediaRequest) throws PlaylistNotFoundException;

    ShufflePlaylistResponse shuffle(ShufflePlaylistRequest request) throws PlaylistNotFoundException;

    List<Media> getAllMedia(Long playlistId) throws PlaylistNotFoundException;
}
