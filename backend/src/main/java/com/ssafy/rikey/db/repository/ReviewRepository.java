package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.BikeRoad;
import com.ssafy.rikey.db.entity.Review;
import com.ssafy.rikey.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findById(Long reviewId);
    List<Review> findByUser(User user);
    List<Review> findByBikeRoad(BikeRoad bikeRoad);
}
