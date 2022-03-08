package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepository articleRepository;

    @Override
    public List<ArticleResponseDto> getArticles(String category) {
        List<Article> articles = articleRepository.findAll();
        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    @Override
    public ArticleResponseDto getArticle(Long articleId) {
        Article article = articleRepository.findById(articleId).get();
        article.increaseHits();
        return new ArticleResponseDto(article);
    }

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

    @Override
    public Long updateArticle(Long articleId, ArticleRequestDto articleRequestDto) {
        Article article = articleRepository.findById(articleId).get();
        article.update(articleRequestDto.getTitle(), articleRequestDto.getContent(), articleRequestDto.getCategory());
        return articleId;
    }

    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
