package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.api.response.ArticleResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.rikey.db.entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findTop3ByCategoryOrderByHitsDesc();
    List<Article> findTop3ByOrderByHitsDesc();
    List<Article> findAllOrderByIdDesc();
    List<Article> findByCategoryOrderByIdDesc(String category);
    List<Article> findTop5ByOrderByIdDesc();
}
