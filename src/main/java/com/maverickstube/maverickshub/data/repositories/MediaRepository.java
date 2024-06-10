package com.maverickstube.maverickshub.data.repositories;

import com.maverickstube.maverickshub.data.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    @Query("SELECT m FROM Media m where m.uploader.id=:userId")
    List<Media> findAllMediaFor(Long userId);
    

    List<Media> findByPlaylistId(Long playlistId);
}
