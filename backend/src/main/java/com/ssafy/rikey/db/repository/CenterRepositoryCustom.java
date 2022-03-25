package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.BikeRoad;

import java.util.List;

public interface CenterRepositoryCustom {

    List<BikeRoad> findBikeRoadByCenterByRange(Double latitude, Double longitude);
}
