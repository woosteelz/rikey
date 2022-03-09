package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateReviewRequestDto;
import com.ssafy.rikey.api.request.UpdateReviewRequestDto;
import com.ssafy.rikey.api.service.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ReviewController
 */
@Api(tags = "Review", value = "리뷰 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 201, message = "success"),
            @ApiResponse(code = 400, message = "fail")
    })
    @ApiOperation(value = "자전거길 리뷰 등록", notes = "특정 자전거 길에 대한 리뷰를 등록한다.")
    public ResponseEntity<String> createReview(
            @RequestBody @ApiParam(value="리뷰 정보") CreateReviewRequestDto reviewInfo) {

        try {
            reviewService.createReview(reviewInfo, user);
            return new ResponseEntity<String>("sucess", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "fail"),
    })
    @ApiOperation(value = "자전거길 리뷰 수정", notes = "등록된 특정 자전거 길에 대한 리뷰를 수정한다.")
    public ResponseEntity<String> updateReview(
            @RequestBody @ApiParam(value="리뷰 정보") UpdateReviewRequestDto reviewInfo,
            @PathVariable("reviewId") @ApiParam(value="리뷰 ID", required = true) Long reviewId) {

        try {
            reviewService.updateReview(reviewInfo, reviewId);
            return new ResponseEntity<String>("sucess", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(code = 200, message = "success"),
            @ApiResponse(code = 400, message = "fail"),
    })
    @ApiOperation(value = "자전거길 리뷰 삭제", notes = "등록된 특정 자전거 길에 대한 리뷰를 삭제한다.")
    public ResponseEntity<String> deleteReview(
            @PathVariable("reviewId") @ApiParam(value="리뷰 ID", required = true) Long reviewId) {

        try {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<String>("sucess", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("fail", HttpStatus.BAD_REQUEST);
        }
    }

}
