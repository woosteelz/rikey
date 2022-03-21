package com.ssafy.rikey.api.service;

import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Likey;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.ArticleRepository;
import com.ssafy.rikey.db.repository.LikeyRepository;
import com.ssafy.rikey.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class LikeyServiceImpl implements LikeyService {

    private final LikeyRepository likeyRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    // 좋아요 등록
    @Transactional
    @Override
    public void createLikey(String userId, Long articleId) {
        Article article = articleRepository.findById(articleId).get();
        User user = userRepository.findById(userId).get();
        Likey likey = Likey.builder()
                .user(user)
                .article(article)
                .build();
        likeyRepository.save(likey);
    }
}
