package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.UpdateUserRequestDto;
import com.ssafy.rikey.api.request.UserRequestDto;
import com.ssafy.rikey.api.response.UserRankingResponseDto;
import com.ssafy.rikey.api.response.UserResponseDto;
import com.ssafy.rikey.api.response.UserSimpleResponseDto;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {

    UserSimpleResponseDto register(UserRequestDto userRequestDto);
    UserSimpleResponseDto login(String authId);
    void updateUserProfile(UpdateUserRequestDto updateUserRequestDto);
    UserResponseDto getUserProfile(String nickName);
    void deleteUser(String userId);
    List<Integer> getRankings(String nickname, String area);
    String uploadImage(MultipartFile multipartFile) throws Exception;
}
