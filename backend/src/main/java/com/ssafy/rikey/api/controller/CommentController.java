package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.api.service.CommentService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Api(tags = "Comment", value = "댓글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 204, message = "댓글 작성 오류"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> createComment(
            @RequestBody @ApiParam(value="댓글 정보") CreateCommentRequestDto commentInfo) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        Long commentId = null;

        try {
            commentId = commentService.createComment(commentInfo);
            httpStatus = HttpStatus.CREATED;
            result.put("status", "SUCCESS");
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

        result.put("comment", commentId);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PutMapping("/{commentId}")
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 204, message = "댓글 작성 오류"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> updateComment(
            @RequestBody @ApiParam(value="댓글 정보") CreateCommentRequestDto commentInfo,
            @PathVariable("commentId") @ApiParam(value="댓글 id", required = true) Long commentId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            // 유저 확인 로직 필요
            commentService.updateComment(commentInfo, commentId);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
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

    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> deleteComment(
            @PathVariable("commentId") @ApiParam(value="댓글 id", required = true) Long commentId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            // 유저 확인 로직 필요
            commentService.deleteComment(commentId);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
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
