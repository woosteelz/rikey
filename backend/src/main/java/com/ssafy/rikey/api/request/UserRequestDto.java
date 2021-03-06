package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("UserRequestDto")
public class UserRequestDto {

    @ApiModelProperty(value = "네이버 발급 고유 일련번호", example = "xxxxx")
    private String authId;

    @ApiModelProperty(value = "닉네임", example = "싸피")
    private String nickName;

    @ApiModelProperty(value = "소개말", example = "한강 라이더입니다")
    private String greeting;

    @ApiModelProperty(value = "지역", example = "SEOUL")
    private String area;

    @ApiModelProperty(value = "키", example = "180")
    private int height;

    @ApiModelProperty(value = "몸무게", example = "80")
    private int weight;
}
