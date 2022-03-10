package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateReviewRequestDto;
import com.ssafy.rikey.api.request.UpdateReviewRequestDto;
import com.ssafy.rikey.api.service.ReviewService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Review", value = "리뷰 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ApiOperation(value = "자전거길 리뷰 등록", notes = "특정 자전거 길에 대한 리뷰를 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 400, message = "실패")
    })
    public ResponseEntity<String> createReview(
            @RequestBody @ApiParam(value="리뷰 정보") CreateReviewRequestDto reviewInfo) {

        try {
            reviewService.createReview(reviewInfo, user);
            return new ResponseEntity<String>("성공", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("실패", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ApiOperation(value = "자전거길 리뷰 수정", notes = "등록된 특정 자전거 길에 대한 리뷰를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "실패"),
    })
    public ResponseEntity<String> updateReview(
            @RequestBody @ApiParam(value="리뷰 정보") UpdateReviewRequestDto reviewInfo,
            @PathVariable("reviewId") @ApiParam(value="리뷰 ID", required = true) Long reviewId) {

        try {
            reviewService.updateReview(reviewInfo, reviewId);
            return new ResponseEntity<String>("성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("실패", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ApiOperation(value = "자전거길 리뷰 삭제", notes = "등록된 특정 자전거 길에 대한 리뷰를 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "실패"),
    })
    public ResponseEntity<String> deleteReview(
            @PathVariable("reviewId") @ApiParam(value="리뷰 ID", required = true) Long reviewId) {

        try {
            reviewService.deleteReview(reviewId);
            return new ResponseEntity<String>("성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("실패", HttpStatus.BAD_REQUEST);
        }
    }

}
