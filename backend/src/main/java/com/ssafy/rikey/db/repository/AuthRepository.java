package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, String> {
}
