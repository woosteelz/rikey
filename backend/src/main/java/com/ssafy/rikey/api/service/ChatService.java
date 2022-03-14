package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.ChatDetailResponseDto;
import com.ssafy.rikey.api.response.ChatResponseDto;
import com.ssafy.rikey.db.entity.User;

import java.util.List;

public interface ChatService {

    List<ChatResponseDto> getChats(String userId);
}
