package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Auth;
import com.ssafy.rikey.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

    User findByAuth(Auth auth);
    User findByNickName(String nickName);
}
