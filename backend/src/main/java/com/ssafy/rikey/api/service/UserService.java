package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.UserResponseDto;
import com.ssafy.rikey.db.entity.User;

public interface UserService {

    UserResponseDto login(String authId);
    UserResponseDto getUser(Long userId);
}
