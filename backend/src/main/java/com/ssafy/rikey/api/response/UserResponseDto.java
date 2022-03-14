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
    private int weeklyDistance;

    @ApiModelProperty(value = "유저 누적 시간", example = "200")
    private int weeklyTime;

    @ApiModelProperty(value = "유저 지역", example = "서울")
    private Area area;

    @ApiModelProperty(value = "유저가 작성한 게시글 리스트")
    @ElementCollection
    private List<ArticleResponseDto> myArticles;

    @ApiModelProperty(value = "유저가 작성한 댓글 리스트")
    @ElementCollection
    private List<CommentResponseDto> myComments;

    @ApiModelProperty(value = "유저가 작성한 리뷰 리스트")
    @ElementCollection
    private List<ReviewResponseDto> myReviews;

    @ApiModelProperty(value = "유저의 주행 리스트")
    @ElementCollection
    private List<RidingInfoResponseDto> myRidings;


    public UserResponseDto(User user,
                           List<ArticleResponseDto> articles,
                           List<CommentResponseDto> comments,
                           List<ReviewResponseDto> reviews,
                           List<RidingInfoResponseDto> ridings) {
        id = user.getId();
        nickName = user.getNickName();
        profilePic = user.getProfile_pic();
        greeting = user.getGreeting();
        weeklyCalories = user.getCumulCarlorie();
        weeklyDistance = user.getCumulDistance();
        weeklyTime = user.getCumulTime();
        area = user.getArea();
        myArticles = articles;
        myComments = comments;
        myReviews = reviews;
        myRidings = ridings;
    }
}
