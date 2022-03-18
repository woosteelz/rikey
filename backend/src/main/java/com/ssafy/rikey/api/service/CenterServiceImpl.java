package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.CenterDetailResponseDto;
import com.ssafy.rikey.api.response.CenterResponseDto;
import com.ssafy.rikey.api.response.CvsResponseDto;
import com.ssafy.rikey.db.entity.Center;
import com.ssafy.rikey.db.entity.Cvs;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.CenterRepository;
import com.ssafy.rikey.db.repository.CvsRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CenterServiceImpl implements CenterService {

    private final CenterRepository centerRepository;
    private final CvsRepository cvsRepository;

    // 코스에 포함되있는 인증센터 리스트 조회
    @Override
    public List<CenterResponseDto> getCenters(Long bikeroadId) {
        List<Center> centers = centerRepository.findByBikeroadId(bikeroadId);
        return centers.stream().map(CenterResponseDto::new).collect(Collectors.toList());
    }

    // 인증센터 상세 조회
    @Override
    public CenterDetailResponseDto getCenter(Long centerId) {
        Center center = centerRepository.findById(centerId).get();
        Double latitude = center.getLatitude();
        Double longitude = center.getLongitude();

        List<Cvs> cvss = cvsRepository.findByRange(latitude, longitude);
        List<CvsResponseDto> cvsResponseDtos = cvss.stream().map(CvsResponseDto::new).collect(Collectors.toList());

        return new CenterDetailResponseDto(center, cvsResponseDtos);
    }
}
