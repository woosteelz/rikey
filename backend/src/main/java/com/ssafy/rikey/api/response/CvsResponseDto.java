package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Cvs;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("CvsResponseDto")
public class CvsResponseDto {

    @ApiModelProperty(value = "편의점 브랜드명", example = "CU")
    private String brandName;

    @ApiModelProperty(value = "편의점명", example = "CU노들역점")
    private String name;

    @ApiModelProperty(value = "편의점 주소", example = "서울시 노들동 노들역")
    private String address;

    @ApiModelProperty(value = "편의점 도로명 주소", example = "서울시 노들동 노들로 1")
    private String roadAddress;

    @ApiModelProperty(value = "편의점 위도", example = "170.34")
    private Double latitude;

    @ApiModelProperty(value = "편의점 경도", example = "170.34")
    private Double longitude;

    public CvsResponseDto(Cvs cvs) {
        brandName = cvs.getBrandName();
        name = cvs.getName();
        address = cvs.getAddress();
        roadAddress = cvs.getRoadAddress();
        latitude = cvs.getLatitude();
        longitude = cvs.getLongitude();
    }
}
