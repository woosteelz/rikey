package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleDetailResponseDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Category;
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

    // 최근 게시글 조회
    @Override
    public List<ArticleResponseDto> getRecentArticles() {
        List<Article> articles = articleRepository.findTop5ByOrderByIdDesc();
        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    // 전체 게시글 조회
    @Override
    public List<ArticleResponseDto> getArticles(String category) {
        List<Article> articles = null;

        if (category.equals("ALL")) {
            articles = articleRepository.findTop3ByOrderByHitsDesc();
            articles.addAll(articleRepository.findAllByOrderByIdDesc());
        } else {
            articles = articleRepository.findTop3ByCategoryOrderByHitsDesc(Category.valueOf(category));
            articles.addAll(articleRepository.findByCategoryOrderByIdDesc(Category.valueOf(category)));
        }

        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    // 게시글 상세 조회
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

    // 게시글 등록
    @Transactional
    @Override
    public Long createArticle(User user, ArticleRequestDto articleRequestDto) {
        Article article = Article.builder()
                .title(articleRequestDto.getTitle())
                .content(articleRequestDto.getContent())
                .category(Category.valueOf(articleRequestDto.getCategory()))
                .user(user)
                .build();
        Article saveArticle = articleRepository.save(article);
        return saveArticle.getId();
    }

    // 게시글 수정
    @Transactional
    @Override
    public void updateArticle(Long articleId, ArticleRequestDto articleRequestDto) {
        Article article = articleRepository.findById(articleId).get();
        article.update(articleRequestDto.getTitle(), articleRequestDto.getContent(), Category.valueOf(articleRequestDto.getCategory()));
    }

    // 게시글 삭제
    @Transactional
    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
