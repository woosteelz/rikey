package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.CvsResponseDto;
import org.locationtech.jts.io.ParseException;

import java.util.List;

public interface CvsService {

    List<CvsResponseDto> getCvss(Double latitude, Double longitude);
}
