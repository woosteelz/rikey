package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Center;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CenterRepository extends JpaRepository<Center, Long>, CenterRepositoryCustom {
}
