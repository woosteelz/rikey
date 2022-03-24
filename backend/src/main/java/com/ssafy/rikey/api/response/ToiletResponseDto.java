package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Toilet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("ToiletResponseDto")
public class ToiletResponseDto {

    @ApiModelProperty(value = "화장실명", example = "노들역화장실")
    private String name;

    @ApiModelProperty(value = "화장실 도로명 주소", example = "서울시 노들동 노들로 1")
    private String roadAddress;

    @ApiModelProperty(value = "화장실 주소", example = "서울시 노들동 노들역")
    private String address;

    @ApiModelProperty(value = "화장실 개방시간", example = "24시간")
    private String openTime;

    @ApiModelProperty(value = "화장실 위도", example = "170.34")
    private Double latitude;

    @ApiModelProperty(value = "화장실 경도", example = "170.34")
    private Double longitude;

    public ToiletResponseDto(Toilet toilet) {
        name = toilet.getName();
        roadAddress = toilet.getRoadAddress();
        address = toilet.getAddress();
        openTime = toilet.getOpenTime();
        latitude = toilet.getLatitude();
        longitude = toilet.getLongitude();
    }
}
