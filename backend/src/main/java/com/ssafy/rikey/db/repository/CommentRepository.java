package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Comment;
import com.ssafy.rikey.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndArticleId(Long id, Long articleId);
    List<Comment> findByUser(User user);
    List<Comment> findByArticleOrderByIdDesc(Article article);
}
