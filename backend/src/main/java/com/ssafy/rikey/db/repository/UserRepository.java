package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Area;
import com.ssafy.rikey.db.entity.Auth;
import com.ssafy.rikey.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {

    User findByAuth(Auth auth);
    User findByNickName(String nickName);
    List<User> findTop3ByAreaOrderByCumulCalorieDesc(Area area);
    List<User> findTop3ByAreaOrderByCumulDistanceDesc(Area area);
    List<User> findTop3ByAreaOrderByCumulTimeDesc(Area area);
}
