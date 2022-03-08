package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateReviewRequestDto;
import com.ssafy.rikey.api.request.UpdateReviewRequestDto;
import com.ssafy.rikey.db.entity.User;

/**
 * ReviewService 인터페이스
 */
public interface ReviewService {

    void createReview(CreateReviewRequestDto reviewInfo, User user);
    void updateReview(UpdateReviewRequestDto reviewInfo, Long reviewId);
    void deleteReview(Long reviewId);
}
