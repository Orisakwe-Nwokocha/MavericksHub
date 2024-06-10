package com.maverickstube.maverickshub.services;

import com.maverickstube.maverickshub.dtos.requests.CreateUserRequest;
import com.maverickstube.maverickshub.dtos.responses.CreateUserResponse;
import com.maverickstube.maverickshub.exceptions.UserNotFoundException;
import com.maverickstube.maverickshub.data.models.User;

public interface UserService {
    CreateUserResponse register(CreateUserRequest request);

    User getById(Long id) throws UserNotFoundException;
}
