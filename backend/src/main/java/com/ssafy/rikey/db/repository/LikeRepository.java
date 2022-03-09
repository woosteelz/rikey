package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long>  {

    List<Like> findByArticle(Long articleId);
}
