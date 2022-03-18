package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.ToiletResponseDto;

import java.util.List;

public interface ToiletService {

    List<ToiletResponseDto> getToilets(Double latitude, Double longitude);
}
