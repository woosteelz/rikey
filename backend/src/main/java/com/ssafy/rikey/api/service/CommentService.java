package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Comment;
import com.ssafy.rikey.db.entity.User;


/**
 * CommentService 인터페이스
 */
public interface CommentService {

    void createComment(CreateCommentRequestDto commentInfo, Article article, User user);
    Comment updateComment(CreateCommentRequestDto commentInfo, Long commentId, Long articleId);
    void deleteComment(Comment comment);
}
