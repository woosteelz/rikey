package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateReviewRequestDto;
import com.ssafy.rikey.api.request.UpdateReviewRequestDto;
import com.ssafy.rikey.api.response.ReviewResponseDto;
import com.ssafy.rikey.api.service.ReviewService;
import com.ssafy.rikey.db.entity.Review;
import com.ssafy.rikey.db.repository.ReviewRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Api(tags = "Review", value = "리뷰 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    @GetMapping("{nickname}")
    @ApiOperation(value = "내 리뷰 조회", notes = "내가 작성한 리뷰를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getMyReviews(
            @PathVariable @ApiParam(value="닉네임", required = true) String nickname) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        List<ReviewResponseDto> reviewList = null;

        try {
            reviewList = reviewService.getMyReviews(nickname);
            httpStatus = HttpStatus.CREATED;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("reviewList", reviewList);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping
    @ApiOperation(value = "자전거길 리뷰 등록", notes = "특정 자전거 길에 대한 리뷰를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 204, message = "리뷰 작성 오류"),
            @ApiResponse(code = 400, message = "리뷰 탐색 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> createReview(
            @RequestBody @ApiParam(value="리뷰 정보") CreateReviewRequestDto reviewInfo) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Long commentId = null;

        try {
            reviewService.createReview(reviewInfo, reviewInfo.getUserId());
            httpStatus = HttpStatus.CREATED;
            result.put("status", "SUCCESS");
        } catch (IllegalArgumentException e) {
            httpStatus = HttpStatus.NO_CONTENT;
            result.put("status", "NO CONTENT");
        } catch (NoSuchElementException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "NO BikeRoad");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PutMapping("/{reviewId}")
    @ApiOperation(value = "자전거길 리뷰 수정", notes = "등록된 특정 자전거 길에 대한 리뷰를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 204, message = "리뷰 작성 오류"),
            @ApiResponse(code = 400, message = "리뷰 탐색 오류"),
            @ApiResponse(code = 403, message = "잘못된 유저"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> updateReview(
            @RequestBody @ApiParam(value="리뷰 정보") UpdateReviewRequestDto reviewInfo,
            @PathVariable("reviewId") @ApiParam(value="리뷰 ID", required = true) Long reviewId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            Review review = reviewRepository.getById(reviewId);
            if (review.getUser().getId().equals(reviewInfo.getUserId())) {
                reviewService.updateReview(reviewInfo, reviewId);
                httpStatus = HttpStatus.OK;
                result.put("status", "SUCCESS");
            } else {
                httpStatus = HttpStatus.FORBIDDEN;
                result.put("status", "WRONG USER");
            }
        } catch (IllegalArgumentException e) {
            httpStatus = HttpStatus.NO_CONTENT;
            result.put("status", "NO CONTENT");
        } catch (NoSuchElementException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "NO ARTICLE");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @DeleteMapping("/{reviewId}")
    @ApiOperation(value = "자전거길 리뷰 삭제", notes = "등록된 특정 자전거 길에 대한 리뷰를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "리뷰 탐색 오류"),
            @ApiResponse(code = 403, message = "잘못된 유저"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> deleteReview(
            @RequestBody @ApiParam(value = "유저 id") Map<String, String> body,
            @PathVariable("reviewId") @ApiParam(value="리뷰 ID", required = true) Long reviewId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            Review review = reviewRepository.getById(reviewId);
            if (review.getUser().getId().equals(body.get("userId"))) {
                reviewService.deleteReview(reviewId);
                httpStatus = HttpStatus.OK;
                result.put("status", "SUCCESS");
            } else {
                httpStatus = HttpStatus.FORBIDDEN;
                result.put("status", "WRONG USER");
            }
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        } catch (Exception e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "BAD REQUEST");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

}
