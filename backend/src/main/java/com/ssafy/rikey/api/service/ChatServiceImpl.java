package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateChatRequestDto;
import com.ssafy.rikey.api.response.ChatDetailResponseDto;
import com.ssafy.rikey.api.response.ChatResponseDto;
import com.ssafy.rikey.db.entity.Chat;
import com.ssafy.rikey.db.entity.ChatMessage;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.entity.UserChat;
import com.ssafy.rikey.db.repository.ChatMessageRepository;
import com.ssafy.rikey.db.repository.ChatRepository;
import com.ssafy.rikey.db.repository.UserChatRepository;
import com.ssafy.rikey.db.repository.UserRepository;
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
    private final UserRepository userRepository;

    // 채팅 목록 조회
    @Override
    public List<ChatResponseDto> getChats(String userId) {

        User user = userRepository.findById(userId).get();

        List<ChatMessage> chatMessages = new ArrayList<>();
        List<UserChat> userChats = userChatRepository.findAllByUser(user);

        for (UserChat userChat : userChats) {
            Chat chat = chatRepository.findById(userChat.getChat().getId()).get();
            ChatMessage chatMessage = chatMessageRepository.findTopByChatOrderByCreatedTimeDesc(chat).get();
            chatMessages.add(chatMessage);
        }

        return chatMessages.stream().map(ChatResponseDto::new).collect(Collectors.toList());
    }

    // 채팅 생성
    @Transactional
    @Override
    public Long createChat(CreateChatRequestDto chatInfo) {

        // 유저 본인
        User user = userRepository.findById(chatInfo.getUserId()).get();
        // 채팅방을 생성할 상대방
        User counterpart = userRepository.findByNickName(chatInfo.getNickName());

        try {
            Chat chat = new Chat();
            chatRepository.save(chat);

            UserChat userChat1 = UserChat.builder()
                    .chat(chat)
                    .user(user)
                    .build();
            userChatRepository.save(userChat1);

            UserChat userChat2 = UserChat.builder()
                    .chat(chat)
                    .user(counterpart)
                    .build();
            userChatRepository.save(userChat2);

            return chat.getId();
        } catch (Exception e) {
            throw e;
        }
    }

    // 채팅 상세 조회
    @Override
    public List<ChatDetailResponseDto> getChat(Long chatId) {

        Chat chat = chatRepository.findById(chatId).get();
        List<ChatMessage> chatMessageList = chatMessageRepository.findAllByChat(chat);

        return chatMessageList.stream().map(ChatDetailResponseDto::new).collect(Collectors.toList());
    }
}
