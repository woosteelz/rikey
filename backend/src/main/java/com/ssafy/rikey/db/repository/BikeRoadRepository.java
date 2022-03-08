package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.BikeRoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BikeRoadRepository extends JpaRepository<BikeRoad, Long> {

    Optional<BikeRoad> findById(Long BikeRoadId);
}
