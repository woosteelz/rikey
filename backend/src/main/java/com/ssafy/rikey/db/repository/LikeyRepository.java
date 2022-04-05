package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Likey;
import com.ssafy.rikey.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeyRepository extends JpaRepository<Likey, Long>  {

    Optional<Likey> findByArticleAndUser(Article article, User user);
    void deleteByUserAndArticle(User user, Article article);
}
