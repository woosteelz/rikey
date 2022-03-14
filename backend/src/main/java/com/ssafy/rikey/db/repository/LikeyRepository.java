package com.ssafy.rikey.db.repository;

import com.ssafy.rikey.db.entity.Likey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeyRepository extends JpaRepository<Likey, Long>  {

    List<Likey> findByArticle(Long articleId);
}
