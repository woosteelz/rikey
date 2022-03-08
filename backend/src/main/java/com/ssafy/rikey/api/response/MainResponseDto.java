package com.ssafy.rikey.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 메인 화면의 날씨, 자전거 안전 수칙 데이터 반환 Dto
 */
@ApiModel("MainResponseDto")
@Data
public class MainResponseDto {

    @ApiModelProperty(name = "지역", example = "서울")
    String area;

    @ApiModelProperty(name = "위도", example = "37.566317724075155")
    double latitude;

    @ApiModelProperty(name = "경도", example = "126.97794079529639")
    double longitude;

    @Builder
    public MainResponseDto(String area, double latitude, double longitude) {

        this.area = area;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
