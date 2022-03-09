package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleDetailResponseDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.api.service.ArticleService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Article", value = "게시글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping
    @ApiOperation(value = "최근 게시글 조회", notes = "최근 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공(CREATED)"),
            @ApiResponse(code = 204, message = "댓글 작성 오류(FAIL)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    public ResponseEntity<Map<String, Object>> getRecentArticles() {

        Map<String, Object> result = new HashMap<>();
        List<ArticleResponseDto> articleList = null;
        HttpStatus httpStatus = null;

        try {
            articleList = articleService.getRecentArticles();
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        result.put("articleList", articleList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping
    @ApiOperation(value = "전체 게시글 조회", notes = "전체 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공(CREATED)"),
            @ApiResponse(code = 204, message = "댓글 작성 오류(FAIL)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    public ResponseEntity<Map<String, Object>> getArticles(
            @RequestParam(required = false) @ApiParam(value = "카테고리") String category) {

        Map<String, Object> result = new HashMap<>();
        List<ArticleResponseDto> articleList = null;
        HttpStatus httpStatus = null;

        try {
            articleList = articleService.getArticles(category);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        result.put("articleList", articleList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/{articleId}")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 id에 해당하는 게시글을 불러온다")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공(CREATED)"),
            @ApiResponse(code = 204, message = "댓글 작성 오류(FAIL)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    public ResponseEntity<Map<String, Object>> getArticle(
            @PathVariable @ApiParam(value = "게시글 id", required = true) Long articleId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        ArticleDetailResponseDto article = null;

        try {
            article = articleService.getArticle(user, articleId);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        result.put("article", article);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping
    @ApiOperation(value = "게시글 작성", notes = "새로운 게시글을 작성한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공(CREATED)"),
            @ApiResponse(code = 204, message = "댓글 작성 오류(FAIL)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    public ResponseEntity<Map<String, Object>> createArticle(
            @RequestBody @ApiParam(value = "게시글 정보", required = true) ArticleRequestDto articleRequestDto) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Long articleId = null;

        try {
            articleId = articleService.createArticle(user, articleRequestDto);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        result.put("article", articleId);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PutMapping("/{articleId}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공(CREATED)"),
            @ApiResponse(code = 204, message = "댓글 작성 오류(FAIL)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    public ResponseEntity<Map<String, Object>> updateArticle(
            @RequestBody @ApiParam(value = "게시글 정보", required = true) ArticleRequestDto articleRequestDto,
            @PathVariable @ApiParam(value = "게시글 id", required = true) Long articleId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        ArticleDetailResponseDto articleDetailResponseDto = null;

        try {
            articleService.updateArticle(articleId, articleRequestDto);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @DeleteMapping("/{articleId}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공(CREATED)"),
            @ApiResponse(code = 204, message = "댓글 작성 오류(FAIL)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    public ResponseEntity<Map<String, Object>> updateArticle(
            @PathVariable @ApiParam(value = "게시글 id", required = true) Long articleId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            articleService.deleteArticle(articleId);
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

