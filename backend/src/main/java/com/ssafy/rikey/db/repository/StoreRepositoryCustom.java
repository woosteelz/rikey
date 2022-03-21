package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Store;

import java.util.List;

public interface StoreRepositoryCustom {

    List<Store> findByRange(Double latitude, Double longitude);
}
