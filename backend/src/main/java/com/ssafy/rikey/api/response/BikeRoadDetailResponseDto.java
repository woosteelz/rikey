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

    @ApiModelProperty(value = "리뷰 점수", example = "3.3")
    private float score;

    @ApiModelProperty(value = "자전거길 소개", example = "한강을 타고 이어지는..")
    private String introduce;

    @ApiModelProperty(value = "코스 이미지", example = "http://j6c208.p.ssafy.io/images/course/1.jpg")
    private String image;

    @ApiModelProperty(value = "자전거길 리뷰 리스트")
    @ElementCollection
    private List<ReviewResponseDto> reviewList;

    @ApiModelProperty(value = "인증센터 리스트")
    @ElementCollection
    private List<CenterResponseDto> centerList;

    @ApiModelProperty(value = "편의점 리스트")
    @ElementCollection
    private List<CvsResponseDto> cvsList;

    @ApiModelProperty(value = "화장실 리스트")
    @ElementCollection
    private List<ToiletResponseDto> toiletList;

    @ApiModelProperty(value = "자전거 보관소 리스트")
    @ElementCollection
    private List<StoreResponseDto> storeList;

    public BikeRoadDetailResponseDto(BikeRoad bikeRoad, List<ReviewResponseDto> reviewResponseDtos, List<CenterResponseDto> centerResponseDtos, List<CvsResponseDto> cvsResponseDtos, List<ToiletResponseDto> toiletResponseDtos, List<StoreResponseDto> storeResponseDtos){
        bikeroadId = bikeRoad.getId();
        name = bikeRoad.getName();
        hour = bikeRoad.getHour();
        minute = bikeRoad.getMinute();
        score = (float)bikeRoad.getScore() / bikeRoad.getCnt();
        introduce = bikeRoad.getIntroduce();
        reviewList = reviewResponseDtos;
        centerList = centerResponseDtos;
        cvsList = cvsResponseDtos;
        toiletList = toiletResponseDtos;
        storeList = storeResponseDtos;
    }
}
