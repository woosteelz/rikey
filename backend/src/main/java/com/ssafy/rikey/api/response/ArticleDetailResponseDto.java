package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Category;

import javax.persistence.ElementCollection;
import java.time.LocalDateTime;
import java.util.List;

public class ArticleDetailResponseDto {

    private Long articleId;

    private String title;

    private String content;

    private LocalDateTime createdTime;

    private LocalDateTime modifiedTime;

//    private Boolean isLike;

    private int hit;

    private int likeCnt;

    private Category category;

    private String author;

//    @ElementCollection
//    private List<commentResponseDto> commentList;

    public ArticleDetailResponseDto(Article article){
        articleId = article.getId();
        title = article.getTitle();
        content = article.getContent();
        createdTime = article.getCreatedTime();
        modifiedTime = article.getModifiedTime();
        hit = article.getHits();
        category = article.getCategory();
        likeCnt = article.getLikeUsers().size();
        author = article.getAuthor().getNickName();
    }
}
