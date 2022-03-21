package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.UpdateUserRequestDto;
import com.ssafy.rikey.api.request.UserRequestDto;
import com.ssafy.rikey.api.response.UserRankingResponseDto;
import com.ssafy.rikey.api.response.UserResponseDto;
import com.ssafy.rikey.api.response.UserSimpleResponseDto;

import java.util.List;

public interface UserService {

    UserSimpleResponseDto register(UserRequestDto userRequestDto);
    UserSimpleResponseDto login(String authId);
    void updateUserProfile(UpdateUserRequestDto updateUserRequestDto);
    UserResponseDto getUserProfile(String nickName);
    void deleteUser(String userId);
    List<UserRankingResponseDto> getRankingsByCalorie(String area);
    List<UserRankingResponseDto> getRankingsByDistance(String area);
    List<UserRankingResponseDto> getRankingsByTime(String area);
}
