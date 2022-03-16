package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Category;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel("ArticleDetailResponseDto")
public class ArticleDetailResponseDto {

    @ApiModelProperty(value = "게시글 id", example = "1")
    private Long articleId;

    @ApiModelProperty(value = "게시글 제목", example = "제목")
    private String title;

    @ApiModelProperty(value = "게시글 내용", example = "내용")
    private String content;

    @ApiModelProperty(value = "게시글 작성일", example = "2022-02-01 23:59:59.500")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "게시글 수정일", example = "2022-02-01 23:59:59.500")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "게시글 좋아요수", example = "1")
    private Boolean isLike;

    @ApiModelProperty(value = "게시글 조회수", example = "1")
    private int hit;

    @ApiModelProperty(value = "게시글 좋아요수", example = "1")
    private int likeCnt;

    @ApiModelProperty(value = "게시글 카테고리", example = "FREE")
    private Category category;

    @ApiModelProperty(value = "게시글 작성자 닉네임", example = "영하")
    private String author;

    @ApiModelProperty(value = "댓글 리스트")
    @ElementCollection
    private List<CommentResponseDto> commentList;

    public ArticleDetailResponseDto(Boolean isLike, Article article,
                                    List<CommentResponseDto> commentResponseDtos){
        articleId = article.getId();
        title = article.getTitle();
        content = article.getContent();
        createdTime = article.getCreatedTime();
        modifiedTime = article.getUpdatedTime();
        hit = article.getHits();
        category = article.getCategory();
        likeCnt = article.getLikeUsers().size();
        author = article.getAuthor().getNickName();
        this.isLike = isLike;
        commentList = commentResponseDtos;
    }
}
