package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.api.response.CommentResponseDto;
import com.ssafy.rikey.db.entity.Comment;

import java.util.List;


/**
 * CommentService 인터페이스
 */
public interface CommentService {

    Long createComment(CreateCommentRequestDto commentInfo);
    Comment updateComment(CreateCommentRequestDto commentInfo, Long commentId);
    void deleteComment(Long commentId);
    List<CommentResponseDto> getCommentList(Long articleId);
}
