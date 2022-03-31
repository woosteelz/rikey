package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Review;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel("ReviewResponseDto")
public class ReviewResponseDto {

    @ApiModelProperty(value = "리뷰 id", example = "1")
    private Long reviewId;

    @ApiModelProperty(value = "리뷰 내용", example = "여기 좋아요!")
    private String content;

    @ApiModelProperty(value = "평점", example = "4")
    private int score;

    @ApiModelProperty(value = "리뷰 작성자 닉네임", example = "yeongha")
    private String author;

    @ApiModelProperty(value = "유저 사진 경로", example = "ftpServerUrl/pic.jpg")
    private String profilePic;

    @ApiModelProperty(value = "리뷰 작성일", example = "2022-02-01-23:59:59")
    private LocalDateTime createdTime;

    public ReviewResponseDto(Review review) {
        reviewId = review.getId();
        content = review.getContent();
        score = review.getScore();
        author = review.getUser().getNickName();
        profilePic = review.getUser().getProfile_pic();
        createdTime = review.getCreatedTime();
    }
}
