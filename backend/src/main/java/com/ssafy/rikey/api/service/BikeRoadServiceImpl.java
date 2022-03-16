package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.BikeRoadDetailResponseDto;
import com.ssafy.rikey.api.response.BikeRoadResponseDto;
import com.ssafy.rikey.api.response.ReviewResponseDto;
import com.ssafy.rikey.db.entity.BikeRoad;
import com.ssafy.rikey.db.entity.Review;
import com.ssafy.rikey.db.repository.BikeRoadRepository;
import com.ssafy.rikey.db.repository.CenterRepository;
import com.ssafy.rikey.db.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class BikeRoadServiceImpl implements BikeRoadService {

    private final BikeRoadRepository bikeRoadRepository;
    private final ReviewRepository reviewRepository;
    private final CenterRepository centerRepository;

    // 추천 코스 리스트 조회
    @Override
    public List<BikeRoadResponseDto> getBikeRoads(Double latitude, Double longitude) {
        List<BikeRoad> bikeRoads = bikeRoadRepository.findBikeRoadByRange(latitude, longitude);
        List<BikeRoad> bikeRoadByCenterByRange = centerRepository.findBikeRoadByCenterByRange(latitude, longitude);
        bikeRoads.addAll(bikeRoadByCenterByRange);
        HashSet<BikeRoad> bikeRoadSet = new HashSet<BikeRoad>(bikeRoads);
        ArrayList<BikeRoad> finalBikeRoads = new ArrayList<BikeRoad>(bikeRoadSet);
        return finalBikeRoads.stream().map(BikeRoadResponseDto::new).collect(Collectors.toList());
    }

    // 코스 상세 정보 조회
    @Override
    public BikeRoadDetailResponseDto getBikeRoad(Long bikeRoadId) {
        BikeRoad bikeRoad = bikeRoadRepository.findById(bikeRoadId).get();
        List<Review> reviews = reviewRepository.findByBikeRoad(bikeRoad);
        List<ReviewResponseDto> reviewResponseDtos = reviews.stream().map(ReviewResponseDto::new).collect(Collectors.toList());
        return new BikeRoadDetailResponseDto(bikeRoad, reviewResponseDtos);
    }
}
