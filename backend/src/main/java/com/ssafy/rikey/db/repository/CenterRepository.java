package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.api.response.CenterResponseDto;
import com.ssafy.rikey.db.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CenterRepository extends JpaRepository<Center, Long>, CenterRepositoryCustom {

    List<Center> findByBikeroadId(Long bikeroadId);
}
