package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Area;
import com.ssafy.rikey.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;

@Getter
@NoArgsConstructor
@ApiModel("UserResponseDto")
public class UserResponseDto {

    @ApiModelProperty(value = "유저 Id", example = "1232354")
    private String id;

    @ApiModelProperty(value = "유저 닉네임", example = "nickname")
    private String nickName;

    @ApiModelProperty(value = "유저 사진 경로", example = "ftpServerUrl/pic.jpg")
    private String profilePic;

    @ApiModelProperty(value = "유저 인사말", example = "안녕하세요. 자전거러버입니다.")
    private String greeting;

    @ApiModelProperty(value = "유저 누적 칼로리", example = "200")
    private int weeklyCalories;

    @ApiModelProperty(value = "유저 누적 거리", example = "200")
    private double weeklyDistance;

    @ApiModelProperty(value = "유저 누적 시간", example = "200")
    private int weeklyTime;

    @ApiModelProperty(value = "유저 지역", example = "서울")
    private Area area;

    @ApiModelProperty(value = "유저 키", example = "180")
    private int height;

    @ApiModelProperty(value = "유저 몸무게", example = "80")
    private int weight;

    public UserResponseDto(User user) {
        id = user.getId();
        nickName = user.getNickName();
        profilePic = user.getProfile_pic();
        greeting = user.getGreeting();
        weeklyCalories = user.getCumulCalorie();
        weeklyDistance = user.getCumulDistance();
        weeklyTime = user.getCumulTime();
        area = user.getArea();
        height = user.getHeight();
        weight = user.getWeight();
    }
}
