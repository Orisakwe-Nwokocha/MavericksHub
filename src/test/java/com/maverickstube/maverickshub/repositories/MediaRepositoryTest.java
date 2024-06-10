package com.maverickstube.maverickshub.repositories;

import com.maverickstube.maverickshub.data.repositories.MediaRepository;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.data.models.Media;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Sql(scripts = {"/db/data.sql"})
@Slf4j
class MediaRepositoryTest {

    @Autowired
    private MediaRepository mediaRepository;

    @Test
    public void findAllMediaFor() throws UserNotFoundException {
        List<Media> media = mediaRepository.findAllMediaFor(200L);
        log.info("items-->{}", media);
        assertThat(media)
                .hasSize(3);
    }
}