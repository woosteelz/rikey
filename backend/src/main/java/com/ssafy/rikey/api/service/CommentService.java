package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.api.response.CommentResponseDto;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Comment;
import com.ssafy.rikey.db.entity.User;

import java.util.List;


/**
 * CommentService 인터페이스
 */
public interface CommentService {

    void createComment(CreateCommentRequestDto commentInfo, Article article, User user);
    Comment updateComment(CreateCommentRequestDto commentInfo, Long commentId, Long articleId);
    void deleteComment(Comment comment);
    List<CommentResponseDto> getCommentList(Long articleId);
}
