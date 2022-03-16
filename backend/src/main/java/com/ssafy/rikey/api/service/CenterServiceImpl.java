package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.BikeRoadResponseDto;
import com.ssafy.rikey.api.response.CenterDetailResponseDto;
import com.ssafy.rikey.api.response.CenterResponseDto;
import com.ssafy.rikey.db.entity.Center;
import com.ssafy.rikey.db.repository.CenterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;

    @Override
    public List<CenterResponseDto> getCenters(Long bikeroadId) {
        List<Center> centers = centerRepository.findByBikeroadId(bikeroadId);
        return centers.stream().map(CenterResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public Center getCenter(Long centerId) {
        Center center = centerRepository.findById(centerId).get();
        return new CenterDetailResponseDto(center, );
    }
}
