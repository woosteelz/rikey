package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Center;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("CenterResponseDto")
public class CenterResponseDto {

    @ApiModelProperty(value = "센터 id", example = "1")
    private Long centerId;

    @ApiModelProperty(value = "센터 이름", example = "한강 인증 센터")
    private String name;

    @ApiModelProperty(value = "센터 위도", example = "170.34")
    private Double latitude;

    @ApiModelProperty(value = "센터 경도", example = "170.34")
    private Double longitude;

    public CenterResponseDto(Center center) {
        centerId = center.getId();
        name = center.getName();
        latitude = center.getLatitude();
        longitude = center.getLongitude();
    }
}
