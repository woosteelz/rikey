package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.*;
import com.ssafy.rikey.db.entity.*;
import com.ssafy.rikey.db.repository.*;
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
    private final CenterCvsRepository centerCvsRepository;
    private final CenterToiletRepository centerToiletRepository;
    private final CenterStoreRepository centerStoreRepository;

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

        List<Center> centers = centerRepository.findByBikeroadId(bikeRoadId);
        List<CenterResponseDto> centerResponseDtos = centers.stream().map(CenterResponseDto::new).collect(Collectors.toList());

        List<CenterCvs> cvss = new ArrayList<>();
        List<CenterToilet> toilets = new ArrayList<>();
        List<CenterStore> stores = new ArrayList<>();
        for(Center center : centers) {
            cvss.addAll(centerCvsRepository.findByCenter(center));
            toilets.addAll(centerToiletRepository.findByCenter(center));
            stores.addAll(centerStoreRepository.findByCenter(center));
        }
        List<CvsResponseDto> cvsResponseDtos = cvss.stream().map((CenterCvs cvs) -> new CvsResponseDto(cvs.getCvs())).collect(Collectors.toList());
        List<ToiletResponseDto> toiletResponseDtos = toilets.stream().map((CenterToilet toilet) -> new ToiletResponseDto(toilet.getToilet())).collect(Collectors.toList());
        List<StoreResponseDto> storeResponseDtos = stores.stream().map((CenterStore store) -> new StoreResponseDto(store.getStore())).collect(Collectors.toList());

        return new BikeRoadDetailResponseDto(bikeRoad, reviewResponseDtos, centerResponseDtos, cvsResponseDtos, toiletResponseDtos, storeResponseDtos);
    }
}
