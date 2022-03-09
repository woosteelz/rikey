package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleDetailResponseDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Like;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.ArticleRepository;
import com.ssafy.rikey.db.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final LikeRepository likeRepository;

    @Override
    public List<ArticleResponseDto> getRecentArticles() {
        List<Article> articles = articleRepository.findTop5ByOrderByIdDesc();
        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public List<ArticleResponseDto> getArticles(String category) {
        List<Article> articles = null;

        if (category == "ALL") {
            articles = articleRepository.findAllOrderByIdDesc();
        } else {
            articles = articleRepository.findByCategoryOrderByIdDesc(category);
        }

        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public ArticleDetailResponseDto getArticle(User user, Long articleId) {
        List<Like> likes = likeRepository.findByArticle(articleId);
        Boolean isLike = false;
        for (Like like : likes) {
            if (like.getUser().equals(user)) {
                isLike = true;
                break;
            }
        }
        Article article = articleRepository.findById(articleId).get();
        article.increaseHits();
        return new ArticleDetailResponseDto(isLike, article);
    }

    @Transactional
    @Override
    public Long createArticle(User user, ArticleRequestDto articleRequestDto) {
        Article article = Article.builder()
                .title(articleRequestDto.getTitle())
                .content(articleRequestDto.getContent())
                .category(articleRequestDto.getCategory())
                .user(user)
                .build();
        Article saveArticle = articleRepository.save(article);
        return saveArticle.getId();
    }

    @Transactional
    @Override
    public void updateArticle(Long articleId, ArticleRequestDto articleRequestDto) {
        Article article = articleRepository.findById(articleId).get();
        article.update(articleRequestDto.getTitle(), articleRequestDto.getContent(), articleRequestDto.getCategory());
    }

    @Transactional
    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
