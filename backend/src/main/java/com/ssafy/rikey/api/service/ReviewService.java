package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateReviewRequestDto;
import com.ssafy.rikey.api.request.UpdateReviewRequestDto;
import com.ssafy.rikey.db.entity.User;

public interface ReviewService {

    void createReview(CreateReviewRequestDto reviewInfo, String userId);
    void updateReview(UpdateReviewRequestDto reviewInfo, Long reviewId);
    void deleteReview(Long reviewId);
}
