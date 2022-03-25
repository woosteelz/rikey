package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long>, StoreRepositoryCustom {
}
