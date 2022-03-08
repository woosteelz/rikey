package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.api.service.CommentService;
import com.ssafy.rikey.db.entity.Comment;
import com.ssafy.rikey.db.repository.CommentRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


/**
 * CommentController
 */
@Api(tags = "Comment", value = "댓글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private CommentService commentService;
    private CommentRepository commentRepository;

    @PostMapping
    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공(CREATED)"),
            @ApiResponse(code = 204, message = "댓글 작성 오류(FAIL)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    public ResponseEntity<String> createComment(
            @RequestBody @ApiParam(value="댓글 정보") CreateCommentRequestDto commentInfo,
            @PathVariable("articleId") @ApiParam(value="게시글 id", required = true) Long articleId) {

        try {
            commentService.createComment(commentInfo, article, user);
            return new ResponseEntity<String>("CREATED", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>("NO ARTICLE", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공(SUCCESS)"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류(NO ARTICLE)"),
    })
    public ResponseEntity<String> updateComment(
            @RequestBody @ApiParam(value="댓글 정보") CreateCommentRequestDto commentInfo,
            @PathVariable("articleId") @ApiParam(value="게시글 id", required = true) Long articleId,
            @PathVariable("commentId") @ApiParam(value="댓글 id", required = true) Long commentId) {

        try {
            Comment comment = commentRepository.findByIdAndArticleId(commentId, articleId).get();

            // 유저 확인 로직 필요
            commentService.updateComment(commentInfo, commentId, articleId);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("NO ARTICLE", HttpStatus.BAD_REQUEST);
        }
    }
}
