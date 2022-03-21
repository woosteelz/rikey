package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Center;
import com.ssafy.rikey.db.entity.CenterCvs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterCvsRepository extends JpaRepository<CenterCvs, Long> {

    List<CenterCvs> findByCenter(Center center);
}
