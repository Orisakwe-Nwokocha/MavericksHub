package com.maverickstube.maverickshub.data.repositories;

import com.maverickstube.maverickshub.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
