package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.ChatResponseDto;
import com.ssafy.rikey.db.entity.Chat;
import com.ssafy.rikey.db.entity.ChatMessage;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.entity.UserChat;
import com.ssafy.rikey.db.repository.ChatMessageRepository;
import com.ssafy.rikey.db.repository.ChatRepository;
import com.ssafy.rikey.db.repository.UserChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ChatServiceImpl implements ChatService{

    private final UserChatRepository userChatRepository;
    private final ChatRepository chatRepository;
    private final ChatMessageRepository chatMessageRepository;

    // 채팅 목록 조회
    @Override
    public List<ChatResponseDto> getChats(User user) {

        List<ChatMessage> chatMessages = new ArrayList<>();
        List<UserChat> userChats = userChatRepository.findAllByUser(user);

        for (UserChat userChat : userChats) {
            Chat chat = chatRepository.findById(userChat.getChat().getId()).get();
            ChatMessage chatMessage = chatMessageRepository.findTopByChatOrderByCreatedTimeDesc(chat).get();
            chatMessages.add(chatMessage);
        }

        return chatMessages.stream().map(ChatResponseDto::new).collect(Collectors.toList());
    }
}
