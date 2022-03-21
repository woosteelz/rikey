package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.BikeRoad;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("BikeRoadDetailResponseDto")
public class BikeRoadDetailResponseDto {

    @ApiModelProperty(value = "자전거길 id", example = "1")
    private Long bikeroadId;

    @ApiModelProperty(value = "자전거길 이름", example = "한강 종주 코스")
    private String name;

    @ApiModelProperty(value = "소요 시간", example = "2")
    private int hour;

    @ApiModelProperty(value = "소요 분", example = "30")
    private int minute;

    @ApiModelProperty(value = "자전거길 소개", example = "한강을 타고 이어지는..")
    private String introduce;

    @ApiModelProperty(value = "자전거길 리뷰 리스트")
    @ElementCollection
    private List<ReviewResponseDto> reviewList;

    public BikeRoadDetailResponseDto(BikeRoad bikeRoad, List<ReviewResponseDto> reviewResponseDtos){
        bikeroadId = bikeRoad.getId();
        name = bikeRoad.getName();
        hour = bikeRoad.getHour();
        minute = bikeRoad.getMinute();
        introduce = bikeRoad.getIntroduce();
        reviewList = reviewResponseDtos;
    }
}
