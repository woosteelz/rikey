package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Article;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel("ArticleResponseDto")
public class ArticleResponseDto {

    @ApiModelProperty(value = "게시글 id", example = "1")
    private Long articleId;

    @ApiModelProperty(value = "게시글 제목", example = "제목")
    private String title;

    @ApiModelProperty(value = "게시글 내용", example = "내용")
    private String content;

    @ApiModelProperty(value = "게시글 작성자 닉네임", example = "영하")
    private String author;

    @ApiModelProperty(value = "게시글 작성일", example = "2022-02-01 23:59:59.500")
    private LocalDateTime createdTime;

    public ArticleResponseDto(Article article){
        articleId = article.getId();
        title = article.getTitle();
        content = article.getContent();
        author = article.getAuthor().getNickName();
        createdTime = article.getCreatedTime();
    }
}
