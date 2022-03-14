package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.UserRequestDto;
import com.ssafy.rikey.api.response.UserResponseDto;
import com.ssafy.rikey.api.response.UserSimpleResponseDto;

public interface UserService {

    UserSimpleResponseDto register(UserRequestDto userRequestDto);
    UserSimpleResponseDto login(String authId);
    UserResponseDto getUser(String userId);
}
