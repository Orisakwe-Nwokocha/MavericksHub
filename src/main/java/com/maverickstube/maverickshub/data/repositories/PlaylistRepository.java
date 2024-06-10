package com.maverickstube.maverickshub.data.repositories;

import com.maverickstube.maverickshub.data.models.Media;
import com.maverickstube.maverickshub.data.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
//    @Query("SELECT m FROM Playlist m where m.uploader.id=:userId")
//    List<Media> findAllPlaylistFor(Long userId);

}
