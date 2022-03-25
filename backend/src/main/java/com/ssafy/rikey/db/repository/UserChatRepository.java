package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.entity.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChatRepository extends JpaRepository<UserChat, Long> {

    List<UserChat> findAllByUser(User user);
}
