package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndArticleId(Long id, Long articleId);
}
