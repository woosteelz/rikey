package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.CvsResponseDto;
import com.ssafy.rikey.db.entity.Cvs;
import com.ssafy.rikey.db.repository.CvsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CvsServiceImpl implements CvsService {

    private final CvsRepository cvsRepository;

    @Override
    public List<CvsResponseDto> getCvss(Double latitude, Double longitude) {
        List<Cvs> cvss = cvsRepository.findByRange(latitude, longitude);
        return cvss.stream().map(CvsResponseDto::new).collect(Collectors.toList());
    }
}
