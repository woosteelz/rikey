package com.ssafy.rikey.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("MainResponseDto")
public class MainResponseDto {

    @ApiModelProperty(name = "지역", example = "서울")
    private String area;

    @ApiModelProperty(name = "위도", example = "37.566317724075155")
    private double latitude;

    @ApiModelProperty(name = "경도", example = "126.97794079529639")
    private double longitude;

    @Builder
    public MainResponseDto(String area, double latitude, double longitude) {
        this.area = area;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
