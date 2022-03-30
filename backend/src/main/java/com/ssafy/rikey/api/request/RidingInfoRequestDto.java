package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("RidingInfoRequestDto")
public class RidingInfoRequestDto {

    @ApiModelProperty(value = "시작 시간", example = "2022-02-01 23:59:59.500")
    private String startTime;

    @ApiModelProperty(value = "종료 시간", example = "2022-02-01 23:59:59.500")
    private String endTime;

    @ApiModelProperty(value = "주행 칼로리", example = "200")
    private int ridingCalorie;

    @ApiModelProperty(value = "주행 시간", example = "200")
    private int ridingTime;

    @ApiModelProperty(value = "주행 거리", example = "200.5")
    private double ridingDist;

    @ApiModelProperty(value = "유저 id", example = "adsfsdf")
    private String userId;
}
