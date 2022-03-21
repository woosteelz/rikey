package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Center;
import com.ssafy.rikey.db.entity.CenterStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterStoreRepository extends JpaRepository<CenterStore, Long> {

    List<CenterStore> findByCenter(Center center);
}
