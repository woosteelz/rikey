package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Category;
import com.ssafy.rikey.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ssafy.rikey.db.entity.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleRepositoryCustom {

    List<Article> findAllByOrderByIdDesc();
    List<Article> findByCategoryOrderByIdDesc(Category category);
    List<Article> findTop5ByOrderByIdDesc();
    List<Article> findByAuthor(User user);
}
