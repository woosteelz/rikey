package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.User;

public interface CommentService {

    void createComment(CreateCommentRequestDto commentInfo, Article article, User user);
}
