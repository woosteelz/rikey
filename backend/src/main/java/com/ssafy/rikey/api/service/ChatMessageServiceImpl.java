package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateChatMessageDto;
import com.ssafy.rikey.db.entity.Chat;
import com.ssafy.rikey.db.entity.ChatMessage;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.ChatMessageRepository;
import com.ssafy.rikey.db.repository.ChatRepository;
import com.ssafy.rikey.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatMessageServiceImpl implements ChatMessageService{

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;
    private final ChatRepository chatRepository;

    // 메시지 보내기(생성)
    @Transactional
    @Override
    public void createChatMessage(CreateChatMessageDto chatMessageInfo, Long chatId) {

        User user = userRepository.findById(chatMessageInfo.getUserId()).get();
        Chat chat = chatRepository.findById(chatId).get();
        ChatMessage chatMessage = ChatMessage.builder()
                .content(chatMessageInfo.getContent().trim())
                .user(user)
                .chat(chat)
                .build();
        chatMessageRepository.save(chatMessage);
    }
}
