package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateReviewRequestDto;
import com.ssafy.rikey.api.request.UpdateReviewRequestDto;
import com.ssafy.rikey.api.response.ReviewResponseDto;
import com.ssafy.rikey.db.entity.Review;
import com.ssafy.rikey.db.entity.User;

import java.util.List;

public interface ReviewService {

    List<ReviewResponseDto> getMyReviews(String nickname);
    void createReview(CreateReviewRequestDto reviewInfo, String userId);
    void updateReview(UpdateReviewRequestDto reviewInfo, Long reviewId);
    void deleteReview(Long reviewId);
}
