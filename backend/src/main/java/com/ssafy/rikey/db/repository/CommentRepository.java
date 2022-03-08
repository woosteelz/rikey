package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * 댓글 레포지토리 정의
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> findByIdAndArticleId(Long id, Long articleId);
}
