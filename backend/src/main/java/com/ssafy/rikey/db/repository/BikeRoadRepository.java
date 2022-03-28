package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.BikeRoad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeRoadRepository extends JpaRepository<BikeRoad, Long>, BikeRoadRepositoryCustom {

}
