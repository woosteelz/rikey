package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleDetailResponseDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.api.service.ArticleService;
import com.ssafy.rikey.api.service.UserService;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.repository.ArticleRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Api(tags = "Article", value = "게시글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/articles")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final UserService userService;

    @GetMapping("/recent")
    @ApiOperation(value = "최근 게시글 조회", notes = "최근 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getRecentArticles() {

        Map<String, Object> result = new HashMap<>();
        List<ArticleResponseDto> articleList = null;
        HttpStatus httpStatus = null;

        try {
            articleList = articleService.getRecentArticles();
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("articleList", articleList);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping
    @ApiOperation(value = "전체 게시글 조회", notes = "전체 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getArticles(
            @RequestParam(required = false) @ApiParam(value = "카테고리") String category) {

        Map<String, Object> result = new HashMap<>();
        List<ArticleResponseDto> articleList = null;
        HttpStatus httpStatus = null;

        try {
            articleList = articleService.getArticles(category);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("articleList", articleList);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/{articleId}")
    @ApiOperation(value = "게시글 상세 조회", notes = "게시글 id에 해당하는 게시글을 불러온다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getArticle(
            @RequestParam @ApiParam(value = "유저 닉네임") String nickName,
            @PathVariable @ApiParam(value = "게시글 id", required = true) Long articleId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        ArticleDetailResponseDto article = null;
        try {
            article = articleService.getArticle(nickName, articleId);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (NoSuchElementException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "NO ARTICLE");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("article", article);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/profile/{nickName}")
    @ApiOperation(value = "내 게시글 조회", notes = "내가 작성한 게시글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getMyArticles(
            @PathVariable @ApiParam(value="닉네임", required = true) String nickName) {

        Map<String, Object> result = new HashMap<>();
        List<ArticleResponseDto> articleList = null;
        HttpStatus httpStatus = null;

        try {
            articleList = articleService.getMyArticles(nickName);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("articleList", articleList);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping
    @ApiOperation(value = "게시글 등록", notes = "새로운 게시글을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 204, message = "게시글 작성 오류"),
            @ApiResponse(code = 403, message = "잘못된 유저"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> createArticle(
            @RequestBody @ApiParam(value = "게시글 정보", required = true) ArticleRequestDto articleRequestDto) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Long articleId = null;

        try {
            articleId = articleService.createArticle(articleRequestDto);
            httpStatus = HttpStatus.CREATED;
            result.put("status", "SUCCESS");
        } catch (IllegalArgumentException e) {
            httpStatus = HttpStatus.NO_CONTENT;
            result.put("status", "NO CONTENT");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("article", articleId);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @ApiOperation(value = "게시글 사진 업로드",notes = "게시글에 사진들을 업로드한다.")
    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> Upload(
            @RequestPart(required = false) List<MultipartFile> uploadFiles) throws Exception {

        Map<String, Object> result = new HashMap<>();
        List<String> urls = null;
        HttpStatus status = null;

        try {
            urls = articleService.uploadImage(uploadFiles);
            status = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (Exception e) {
            e.printStackTrace();
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("urls", urls);
        return new ResponseEntity<Map<String, Object>>(result, status);
    }

    @PutMapping("/{articleId}")
    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 204, message = "게시글 작성 오류"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
            @ApiResponse(code = 403, message = "잘못된 유저"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> updateArticle(
            @RequestBody @ApiParam(value = "게시글 정보", required = true) ArticleRequestDto articleRequestDto,
            @PathVariable @ApiParam(value = "게시글 id", required = true) Long articleId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            Article article = articleRepository.getById(articleId);
            if (article.getAuthor().getId().equals(articleRequestDto.getUserId())) {
                articleService.updateArticle(articleId, articleRequestDto);
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

    @DeleteMapping("/{articleId}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
            @ApiResponse(code = 403, message = "잘못된 유저"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> deleteArticle(
            @RequestBody @ApiParam(value = "유저 id") Map<String, String> body,
            @PathVariable @ApiParam(value = "게시글 id", required = true) Long articleId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            Article article = articleRepository.getById(articleId);
            if (article.getAuthor().getId().equals(body.get("userId"))) {
                articleService.deleteArticle(articleId);
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

    @GetMapping("/rankings/{nickName}")
    @ApiOperation(value = "랭킹 조회", notes = "누적 칼로리, 누적 거리, 누적 시간을 기준으로 랭킹을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getRankings(
            @RequestParam @ApiParam(value = "지역", required = true) String area,
            @PathVariable @ApiParam(value = "유저 닉네임", required = true) String nickName) {

        Map<String, Object> result = new HashMap<>();
        List<Integer> rankings = null;
        HttpStatus httpStatus = null;

        try {
            rankings = userService.getRankings(nickName, area);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (NoSuchElementException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "WRONG USER");
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("rankingByCalorie", rankings.get(0));
        result.put("rankingsByDistance", rankings.get(1));
        result.put("rankingByTime", rankings.get(2));

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/search")
    @ApiOperation(value = "게시글 검색", notes = "검색어로 게시글을 검색한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> searchArticles(
            @RequestParam @ApiParam(value = "검색어", required = true) String keyword) {

        Map<String, Object> result = new HashMap<>();
        List<ArticleResponseDto> articleList = null;
        HttpStatus httpStatus = null;

        try {
            articleList = articleService.searchArticles(keyword);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("articleList", articleList);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}

