package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.MainResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * MainService 구현체
 */
@RequiredArgsConstructor
@Service
public class MainServiceImpl implements MainService{

    @Override
    public MainResponseDto loadMainInfo(String area) {

        double latitude;
        double longitude;

        if (area.equals("서울")) {
            latitude = 37.56631926033728;
            longitude = 126.97796986737619;
        }
        else if (area.equals("세종")) {
            latitude = 36.480323819711025;
            longitude = 127.28917985360516;
        }
        else if (area.equals("인천")) {
            latitude = 37.45618152468616;
            longitude = 126.70593466677659;
        }
        else if (area.equals("대전")) {
            latitude = 36.350594937030124;
            longitude = 127.3848937996265;
        }
        else if (area.equals("대구")) {
            latitude = 35.871629153414375;
            longitude = 128.6017545685198;
        }
        else if (area.equals("광주")) {
            latitude = 35.16019242127408;
            longitude = 126.85148001308879;
        }
        else if (area.equals("울산")) {
            latitude = 35.539731721020026;
            longitude = 129.31150060884363;
        }
        else if (area.equals("부산")) {
            latitude = 35.179992971435006;
            longitude = 129.07500845541836;
        }
        else if (area.equals("경기")) {
            latitude = 37.27502159725917;
            longitude = 127.00916415905169;
        }
        else if (area.equals("강원")) {
            latitude = 37.88552188217517;
            longitude = 127.72976403570132;
        }
        else if (area.equals("충남")) {
            latitude = 36.66043833065671;
            longitude = 126.67362566895159;
        }
        else if (area.equals("충북")) {
            latitude = 36.63592120066803;
            longitude = 127.4913123401156;
        }
        else if (area.equals("전남")) {
            latitude = 34.81629785444392;
            longitude = 126.46290274006749;
        }
        else if (area.equals("전북")) {
            latitude = 35.819969912408254;
            longitude = 127.10813706707609;
        }
        else if (area.equals("제주")) {
            latitude = 33.49965838062919;
            longitude = 126.53125307042643;
        }
        else if (area.equals("경남")) {
            latitude = 35.235607656458754;
            longitude = 128.69185730963713;
        }
        else {
            latitude = 36.57608619394952;
            longitude = 128.5055947053839;
        }

        MainResponseDto mainResponseDto = MainResponseDto.builder()
                .area(area)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        return mainResponseDto;
    }
}
