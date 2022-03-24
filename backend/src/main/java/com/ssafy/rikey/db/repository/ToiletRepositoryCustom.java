package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Toilet;

import java.util.List;

public interface ToiletRepositoryCustom {

    List<Toilet> findByRange(Double latitude, Double longitude);
}
