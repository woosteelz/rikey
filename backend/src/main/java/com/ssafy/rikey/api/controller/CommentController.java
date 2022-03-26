package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.api.response.CommentResponseDto;
import com.ssafy.rikey.api.response.ReviewResponseDto;
import com.ssafy.rikey.api.service.CommentService;
import com.ssafy.rikey.db.entity.Comment;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Api(tags = "Comment", value = "댓글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("{nickName}")
    @ApiOperation(value = "내 댓글 조회", notes = "내가 작성한 댓글을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getMyComments(
            @PathVariable @ApiParam(value="닉네임", required = true) String nickName) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        List<CommentResponseDto> commentList = null;

        try {
            commentList = commentService.getMyComments(nickName);
            httpStatus = HttpStatus.CREATED;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("commentList", commentList);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

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
            @ApiResponse(code = 403, message = "잘못된 유저"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> updateComment(
            @RequestBody @ApiParam(value="댓글 정보") CreateCommentRequestDto commentInfo,
            @PathVariable("commentId") @ApiParam(value="댓글 id", required = true) Long commentId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            Comment comment = commentService.getComment(commentId);
            if (comment.getUser().getId().equals(commentInfo.getUserId())) {
                commentService.updateComment(commentInfo, commentId);
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

    @DeleteMapping("/{commentId}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
            @ApiResponse(code = 403, message = "잘못된 유저"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> deleteComment(
            @RequestBody @ApiParam(value = "유저 id") Map<String, String> body,
            @PathVariable("commentId") @ApiParam(value="댓글 id", required = true) Long commentId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            Comment comment = commentService.getComment(commentId);
            if (comment.getUser().getId().equals(body.get("userId"))) {
                commentService.deleteComment(commentId);
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
