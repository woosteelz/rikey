package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.BikeRoad;

import java.util.List;

public interface BikeRoadRepositoryCustom {

    List<BikeRoad> findBikeRoadByRange(Double latitude, Double longitude);
}
