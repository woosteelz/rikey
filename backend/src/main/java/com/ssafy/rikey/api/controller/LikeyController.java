package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.LikeyRequestDto;
import com.ssafy.rikey.api.service.LikeyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "Like", value = "게시글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeyController {

    private final LikeyService likeyService;

    @ApiOperation(value = "좋아요 등록", notes = "게시글에 좋아요를 등록한다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> createLike(
            @RequestBody @ApiParam(value = "좋아요 정보", required = true) LikeyRequestDto likeyRequestDto) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            likeyService.createLikey(likeyRequestDto.getUserId(), likeyRequestDto.getArticleId());
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @ApiOperation(value = "좋아요 취소", notes = "게시글에 좋아요를 등록한다.")
    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteLike(
            @RequestBody @ApiParam(value = "좋아요 정보", required = true) LikeyRequestDto likeyRequestDto) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            likeyService.deleteLikey(likeyRequestDto.getUserId(), likeyRequestDto.getArticleId());
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

}
