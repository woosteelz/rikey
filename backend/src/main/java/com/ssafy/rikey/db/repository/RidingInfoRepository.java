package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.RidingInfo;
import com.ssafy.rikey.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RidingInfoRepository extends JpaRepository<RidingInfo, Long> {

    List<RidingInfo> findByUser(User user);
}
