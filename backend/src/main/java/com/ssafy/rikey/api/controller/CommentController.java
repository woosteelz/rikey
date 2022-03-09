package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.api.service.CommentService;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Comment;
import com.ssafy.rikey.db.repository.ArticleRepository;
import com.ssafy.rikey.db.repository.CommentRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@Api(tags = "Comment", value = "댓글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;

    @PostMapping
    @ApiOperation(value = "댓글 등록", notes = "새로운 댓글을 등록한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 204, message = "댓글 작성 오류"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
    })
    public ResponseEntity<String> createComment(
            @RequestBody @ApiParam(value="댓글 정보") CreateCommentRequestDto commentInfo,
            @PathVariable("articleId") @ApiParam(value="게시글 id", required = true) Long articleId) {

        try {
            Article article = articleRepository.findById(articleId).get();
            commentService.createComment(commentInfo, article, user);
            return new ResponseEntity<String>("CREATED", HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<String>("FAIL", HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>("NO ARTICLE", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{articleId}")
    @ApiOperation(value = "댓글 수정", notes = "댓글을 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
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

    @DeleteMapping("/{articleId}")
    @ApiOperation(value = "댓글 삭제", notes = "댓글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "게시글 탐색 오류"),
    })
    public ResponseEntity<String> deleteComment(
            @PathVariable("articleId") @ApiParam(value="게시글 id", required = true) Long articleId,
            @PathVariable("commentId") @ApiParam(value="댓글 id", required = true) Long commentId) {

        try {
            Comment comment = commentRepository.findByIdAndArticleId(commentId, articleId).get();

            // 유저 확인 로직 필요
            commentService.deleteComment(comment);
            return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<String>("NO ARTICLE", HttpStatus.BAD_REQUEST);
        }
    }
}
