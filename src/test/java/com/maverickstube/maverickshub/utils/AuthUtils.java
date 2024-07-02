package com.maverickstube.maverickshub.utils;

import com.maverickstube.maverickshub.dtos.requests.CreateUserRequest;
import com.maverickstube.maverickshub.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {
    @Autowired
    private UserService userService;

    public void createUser() {
        CreateUserRequest request = new CreateUserRequest();
        request.setEmail("test@email.com");
        request.setPassword("password");
        userService.register(request);
    }


}
