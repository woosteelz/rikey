package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Article;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<Article> findRecentlyByCategoryOrderByHIts(String category) {
        Query query = em.createNativeQuery("SELECT a.article_id, a.created_time, a.updated_time, a.category, a.content, a.hits, a.title, a.user_id FROM article AS a "
                        + "WHERE a.category = ? AND a.created_time BETWEEN DATE_ADD(NOW(),INTERVAL -1 MONTH ) AND NOW() "
                        + "ORDER BY a.hits DESC LIMIT 3", Article.class)
                .setParameter(1, category);

        List<Article> articles = query.getResultList();
        return articles;
    }

    @Override
    public List<Article> findRecentlyOrderByHIts() {
        System.out.println("들어오니?");
        Query query = em.createNativeQuery("SELECT a.article_id, a.created_time, a.updated_time, a.category, a.content, a.hits, a.title, a.user_id FROM article AS a "
                        + "WHERE a.created_time BETWEEN DATE_ADD(NOW(),INTERVAL -1 MONTH ) AND NOW() "
                        + "ORDER BY a.hits DESC LIMIT 3", Article.class);

        List<Article> articles = query.getResultList();
        System.out.println("articles = " + articles);
        return articles;
    }
}
