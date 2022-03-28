package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleDetailResponseDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {

    List<ArticleResponseDto> getRecentArticles();
    List<ArticleResponseDto> getArticles(String category);
    ArticleDetailResponseDto getArticle(String nickName, Long articleId);
    List<ArticleResponseDto> getMyArticles(String nickname);
    List<ArticleResponseDto> searchArticles(String keyword);
    Long createArticle(ArticleRequestDto articleRequestDto);
    void updateArticle(Long articleId, ArticleRequestDto articleRequestDto);
    void deleteArticle(Long articleId);
    List<String> uploadImage(List<MultipartFile> uploadFiles) throws Exception;
}
