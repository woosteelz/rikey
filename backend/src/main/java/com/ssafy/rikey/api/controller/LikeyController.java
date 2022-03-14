package com.ssafy.rikey.api.controller;

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

    public LikeyService likeService;

    @ApiOperation(value = "좋아요 등록", notes = "게시글에 좋아요를 등록한다.")
    @PostMapping
    public ResponseEntity<Map<String, Object>> createLike(
            @RequestBody @ApiParam(value = "유저 아이디") String userId,
            @RequestBody @ApiParam(value = "게시글 id", required = true) Long articleId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            likeService.createLikey(userId, articleId);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

}
