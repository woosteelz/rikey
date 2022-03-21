package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToiletRepository extends JpaRepository<Toilet, Long>, ToiletRepositoryCustom {
}
