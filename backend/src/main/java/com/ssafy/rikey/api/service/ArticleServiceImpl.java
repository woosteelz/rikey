package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleDetailResponseDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.api.response.CommentResponseDto;
import com.ssafy.rikey.db.entity.*;
import com.ssafy.rikey.db.repository.ArticleRepository;
import com.ssafy.rikey.db.repository.CommentRepository;
import com.ssafy.rikey.db.repository.LikeyRepository;
import com.ssafy.rikey.db.repository.UserRepository;
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
    private final LikeyRepository likeRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

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
    public ArticleDetailResponseDto getArticle(String userId, Long articleId) {
        List<Likey> likeys = likeRepository.findByArticle(articleId);
        Boolean isLike = false;

        for (Likey likey : likeys) {
            if (likey.getUser().getId().equals(userId)) {
                isLike = true;
                break;
            }
        }
        Article article = articleRepository.findById(articleId).get();
        article.increaseHits();

        List<Comment> comments = commentRepository.findByArticle(article);
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());

        return new ArticleDetailResponseDto(isLike, article, commentResponseDtos);
    }

    // 게시글 등록
    @Transactional
    @Override
    public Long createArticle(String userId, ArticleRequestDto articleRequestDto) {
        User user = userRepository.findById(userId).get();

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
