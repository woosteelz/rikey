package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Article;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ArticleResponseDto {

    private Long articleId;

    private String title;

    private String content;

    private LocalDateTime createdTime;

    private int likeCnt;

    private String author;

    public ArticleResponseDto(Article article){
        articleId = article.getId();
        title = article.getTitle();
        content = article.getContent();
        createdTime = article.getCreatedTime();
        likeCnt = article.getLikeUsers().size();
        author = article.getAuthor().getNickName();
    }
}
