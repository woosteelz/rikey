package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateReviewRequestDto;
import com.ssafy.rikey.api.request.UpdateReviewRequestDto;
import com.ssafy.rikey.api.response.ReviewResponseDto;
import com.ssafy.rikey.db.entity.BikeRoad;
import com.ssafy.rikey.db.entity.Review;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.BikeRoadRepository;
import com.ssafy.rikey.db.repository.ReviewRepository;
import com.ssafy.rikey.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final BikeRoadRepository bikeRoadRepository;
    private final UserRepository userRepository;

    // 내 리뷰 조회
    @Override
    public List<ReviewResponseDto> getMyReviews(String nickName) {
        User user = userRepository.findByNickName(nickName);
        List<Review> reviews = reviewRepository.findByUser(user);
        return reviews.stream().map(ReviewResponseDto::new).collect(Collectors.toList());
    }

    // 리뷰 등록 서비스
    @Override
    public void createReview(CreateReviewRequestDto reviewInfo, String userId) {
        User user = userRepository.findById(userId).get();

        try {
            BikeRoad bikeRoad = bikeRoadRepository.findById(reviewInfo.getBikeRoadId()).get();
            bikeRoad.update(bikeRoad.getScore() + reviewInfo.getScore(), bikeRoad.getCnt() + 1);
            Review review = Review.builder()
                    .content(reviewInfo.getContent())
                    .score(reviewInfo.getScore())
                    .bikeRoad(bikeRoad)
                    .user(user)
                    .build();
            reviewRepository.save(review);
        } catch (Exception e) {
            throw e;
        }
    }

    // 리뷰 수정 서비스
    @Override
    public void updateReview(UpdateReviewRequestDto reviewInfo, Long reviewId) {

        try {
            Review review = reviewRepository.findById(reviewId).get();
            BikeRoad bikeRoad = review.getBikeRoad();
            bikeRoad.update(bikeRoad.getScore() - review.getScore() + reviewInfo.getScore(), bikeRoad.getCnt());
            review.update(reviewInfo.getContent(), reviewInfo.getScore());
        } catch (Exception e) {
            throw e;
        }
    }

    // 리뷰 삭제 서비스
    @Override
    public void deleteReview(Long reviewId) {

        try {
            Review review = reviewRepository.findById(reviewId).get();
            BikeRoad bikeRoad = review.getBikeRoad();
            bikeRoad.update(bikeRoad.getScore() - review.getScore(), bikeRoad.getCnt() - 1);
            reviewRepository.delete(review);
        } catch (Exception e) {
            throw e;
        }
    }
}
