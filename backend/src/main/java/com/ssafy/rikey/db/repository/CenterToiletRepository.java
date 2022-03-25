package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Center;
import com.ssafy.rikey.db.entity.CenterToilet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterToiletRepository extends JpaRepository<CenterToilet, Long> {

    List<CenterToilet> findByCenter(Center center);
}
