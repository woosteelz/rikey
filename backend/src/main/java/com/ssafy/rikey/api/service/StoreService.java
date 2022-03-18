package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.StoreResponseDto;

import java.util.List;

public interface StoreService {

    List<StoreResponseDto> getStores(Double latitude, Double longitude);
}
