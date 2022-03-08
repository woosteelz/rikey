package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.MainResponseDto;

/**
 * MainService 인터페이스
 */
public interface MainService {

    MainResponseDto loadMainInfo(String area);
}
