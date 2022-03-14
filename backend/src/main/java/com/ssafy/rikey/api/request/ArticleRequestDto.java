package com.ssafy.rikey.api.request;

import com.ssafy.rikey.db.entity.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel("ArticleRequestDto")
public class ArticleRequestDto {

    @ApiModelProperty(value = "게시글 제목", example = "제목")
    private String title;

    @ApiModelProperty(value = "게시글 내용", example = "내용")
    private String content;

    @ApiModelProperty(value = "게시글 카테고리", example = "FREE")
    private String category;

    @ApiModelProperty(value = "유저 id", example = "adsfsdf")
    private String userId;

}
