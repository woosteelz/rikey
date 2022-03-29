package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.BikeRoad;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("BikeRoadResponseDto")
public class BikeRoadResponseDto {

    @ApiModelProperty(value = "자전거길 id", example = "1")
    private Long bikeroadId;

    @ApiModelProperty(value = "자전거길 이름", example = "한강 종주 코스")
    private String name;

    @ApiModelProperty(value = "리뷰 개수", example = "2")
    private int reviewCnt;

    @ApiModelProperty(value = "리뷰 점수", example = "3.3")
    private float score;

    public BikeRoadResponseDto(BikeRoad bikeRoad) {
        bikeroadId = bikeRoad.getId();
        name = bikeRoad.getName();
        reviewCnt = bikeRoad.getReviews().size();
        score = (float)bikeRoad.getScore() / bikeRoad.getCnt();
    }
}
