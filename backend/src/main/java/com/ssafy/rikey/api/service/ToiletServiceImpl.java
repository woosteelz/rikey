package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.CvsResponseDto;
import com.ssafy.rikey.api.response.ToiletResponseDto;
import com.ssafy.rikey.db.entity.Cvs;
import com.ssafy.rikey.db.entity.Toilet;
import com.ssafy.rikey.db.repository.ToiletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ToiletServiceImpl implements ToiletService {

    private final ToiletRepository toiletRepository;

    @Override
    public List<ToiletResponseDto> getToilets(Double latitude, Double longitude) {
        List<Toilet> toilets = toiletRepository.findByRange(latitude, longitude);
        return toilets.stream().map(ToiletResponseDto::new).collect(Collectors.toList());
    }
}
