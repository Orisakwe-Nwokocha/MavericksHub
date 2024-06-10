package com.maverickstube.maverickshub.services;

import com.github.fge.jsonpatch.JsonPatch;
import com.maverickstube.maverickshub.data.models.Playlist;
import com.maverickstube.maverickshub.dtos.requests.AddMediaToPlaylistRequest;
import com.maverickstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.responses.MediaResponse;
import com.maverickstube.maverickshub.dtos.responses.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.exceptions.PlaylistNotFoundException;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.data.models.Media;

import java.util.List;

public interface MediaService {
    UploadMediaResponse upload(UploadMediaRequest request);

    Media getMediaBy(Long id);

    UpdateMediaResponse updateMedia(Long mediaId, JsonPatch updateMediaRequest);

    List<MediaResponse> getMediaFor(Long userId) throws UserNotFoundException;

    List<Media> getMedia(AddMediaToPlaylistRequest addMediaRequest, Playlist playlist) throws PlaylistNotFoundException;

    List<Media> getMediaForPlaylist(Long playlistId) throws PlaylistNotFoundException;
}
