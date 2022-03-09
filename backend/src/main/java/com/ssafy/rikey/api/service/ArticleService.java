package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleDetailResponseDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.db.entity.User;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public interface ArticleService {

    List<ArticleResponseDto> getRecentArticles();
    List<ArticleResponseDto> getArticles(String category);
    ArticleDetailResponseDto getArticle(User user, Long articleId);
    Long createArticle(User user, ArticleRequestDto articleRequestDto);
    void updateArticle(Long articleId, ArticleRequestDto articleRequestDto);
    void deleteArticle(Long articleId);
}
