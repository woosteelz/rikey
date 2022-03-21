package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findById(Long chatId);
}
