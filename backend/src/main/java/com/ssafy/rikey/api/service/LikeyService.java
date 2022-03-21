package com.ssafy.rikey.api.service;

public interface LikeyService {

    void createLikey(String userId, Long articleId);
    void deleteLikey(String userId, Long articleId);
}
