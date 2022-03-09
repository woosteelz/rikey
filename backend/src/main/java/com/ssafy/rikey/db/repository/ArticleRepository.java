package com.ssafy.rikey.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.rikey.db.entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByCategory(String category);
}
