package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.CenterDetailResponseDto;
import com.ssafy.rikey.api.response.CenterResponseDto;
import com.ssafy.rikey.db.entity.Center;

import java.util.List;

public interface CenterService {
    List<CenterResponseDto> getCenters(Long bikeraodId);
    CenterDetailResponseDto getCenter(Long centerId);
}
