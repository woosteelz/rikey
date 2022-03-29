package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateChatMessageDto;

public interface ChatMessageService {

    void createChatMessage(CreateChatMessageDto chatMessageInfo, Long chatId);
}
