package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Cvs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CvsRepository extends JpaRepository<Cvs, Long>, CvsRepositoryCustom {
}
