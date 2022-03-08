package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 리뷰 데이터 생성 요청 Dto
 */
@Getter
@ApiModel("CreateReviewRequestDto")
public class CreateReviewRequestDto {

    @ApiModelProperty(value = "자전거길 ID", example = "1")
    private Long bikeRoadId;

    @ApiModelProperty(value = "리뷰 내용", example = "정말 좋은 코스입니다!")
    private String content;

    @ApiModelProperty(value = "평점", example = "4")
    private int score;
}
