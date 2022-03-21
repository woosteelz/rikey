package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Chat;
import com.ssafy.rikey.db.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    Optional<ChatMessage> findTopByChatOrderByCreatedTimeDesc(Chat chat);
}
