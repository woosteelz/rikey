package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("UpdateReviewRequestDto")
public class UpdateReviewRequestDto {

    @ApiModelProperty(value = "리뷰 내용", example = "정말 좋은 코스입니다!")
    private String content;

    @ApiModelProperty(value = "평점", example = "4")
    private int score;

    @ApiModelProperty(value = "유저 아이디", example = "userId")
    private String userId;
}
