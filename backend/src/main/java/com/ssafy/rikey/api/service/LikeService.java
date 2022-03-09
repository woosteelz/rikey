package com.ssafy.rikey.api.service;

import com.ssafy.rikey.db.entity.User;

public interface LikeService {

    void createLike(User user, Long articleId);
}
