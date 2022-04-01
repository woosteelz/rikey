package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateChatRequestDto;
import com.ssafy.rikey.api.response.ChatDetailResponseDto;
import com.ssafy.rikey.api.response.ChatResponseDto;

import java.util.List;

public interface ChatService {

    List<ChatResponseDto> getChats(String nickName);
    Long createChat(CreateChatRequestDto chatInfo);
    List<ChatDetailResponseDto> getChat(Long chatId);
}
