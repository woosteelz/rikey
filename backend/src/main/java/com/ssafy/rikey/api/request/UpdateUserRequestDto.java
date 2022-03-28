package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("UpdateUserRequestDto")
public class UpdateUserRequestDto {

    @ApiModelProperty(value = "유저 id", example = "xxxxx")
    private String userId;

    @ApiModelProperty(value = "닉네임", example = "싸피")
    private String nickName;

    @ApiModelProperty(value = "소개말", example = "한강 라이더입니다")
    private String greeting;

    @ApiModelProperty(value = "지역", example = "SEOUL")
    private String area;

    @ApiModelProperty(value = "유저 사진 경로", example = "ftpServerUrl/pic.jpg")
    private String profilePic;

    @ApiModelProperty(value = "키", example = "175")
    private int height;

    @ApiModelProperty(value = "몸무게", example = "70")
    private int weight;
}
