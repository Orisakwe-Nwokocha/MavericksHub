package com.maverickstube.maverickshub.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import com.maverickstube.maverickshub.data.models.Playlist;
import com.maverickstube.maverickshub.dtos.requests.AddMediaToPlaylistRequest;
import com.maverickstube.maverickshub.dtos.requests.UploadMediaRequest;
import com.maverickstube.maverickshub.dtos.responses.MediaResponse;
import com.maverickstube.maverickshub.dtos.responses.UpdateMediaResponse;
import com.maverickstube.maverickshub.dtos.responses.UploadMediaResponse;
import com.maverickstube.maverickshub.exceptions.*;
import com.maverickstube.maverickshub.data.models.Media;
import com.maverickstube.maverickshub.data.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@Service
@AllArgsConstructor
@Slf4j
public class MavericksHubMediaService implements MediaService{
    private final MediaRepository mediaRepository;
    private final Cloudinary cloudinary;
    private final ModelMapper modelMapper;
    private final UserService userService;


    @Override
    public UploadMediaResponse upload(UploadMediaRequest request) {
        try {
            Uploader uploader = cloudinary.uploader();
            Map<?,?> response = uploader.upload(request.getMediaFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            String url = response.get("url").toString();
            Media media = modelMapper.map(request, Media.class);
            media.setUrl(url);
            media.setUploader(userService.getById(request.getUserId()));
            media = mediaRepository.save(media);
            return modelMapper.map(media, UploadMediaResponse.class);
        }catch (IOException | UserNotFoundException exception){
            throw new MediaUploadFailedException("media upload failed");
        }
    }


    @Override
    public Media getMediaBy(Long id) {
        return mediaRepository.findById(id)
                .orElseThrow(()->new MediaNotFoundException("media not found"));
    }

    @Override
    public UpdateMediaResponse updateMedia(Long mediaId,
                                           JsonPatch jsonPatch) {
      try {
          //1. get target object
          Media media = getMediaBy(mediaId);
          //2. convert object from above to JsonNode (use ObjectMapper)
          ObjectMapper objectMapper = new ObjectMapper();
          JsonNode mediaNode = objectMapper.convertValue(media, JsonNode.class);
          //3. apply jsonPatch to mediaNode
          mediaNode = jsonPatch.apply(mediaNode);
          //4. convert mediaNode to Media object
          media=objectMapper.convertValue(mediaNode, Media.class);
          media = mediaRepository.save(media);
          return modelMapper.map(media, UpdateMediaResponse.class);
      }catch (JsonPatchException exception){
          throw new MediaUpdateFailedException(exception.getMessage());
      }
    }

    @Override
    public List<MediaResponse> getMediaFor(Long userId) throws UserNotFoundException {
        userService.getById(userId);
        List<Media> media = mediaRepository.findAllMediaFor(userId);
        return media.stream()
                .map(mediaItem->modelMapper.map(mediaItem, MediaResponse.class))
                .toList();
    }


    @Override
    public List<Media> getMedia(AddMediaToPlaylistRequest addMediaRequest, Playlist playlist) throws PlaylistNotFoundException {
        Media media = getMediaBy(addMediaRequest.getMediaId());
        media.setPlaylist(playlist);
        media = mediaRepository.save(media);
        return mediaRepository.findAllMediaForPlaylist(playlist.getId());
    }

    @Override
    public List<Media> getMediaForPlaylist(Long playlistId) throws PlaylistNotFoundException {
        return mediaRepository.findAllMediaForPlaylist(playlistId);
    }
}
