package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.api.response.CommentResponseDto;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Comment;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.ArticleRepository;
import com.ssafy.rikey.db.repository.CommentRepository;
import com.ssafy.rikey.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    // 내 댓글 조회
    @Override
    public List<CommentResponseDto> getMyComments(String nickName) {
        User user = userRepository.findByNickName(nickName);
        List<Comment> comments = commentRepository.findByUser(user);
        return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }

    //댓글 조회
    @Override
    public Comment getComment(Long commentId) {
        Comment comment = commentRepository.getById(commentId);
        return comment;
    }

    // 댓글 등록
    @Transactional
    @Override
    public Long createComment(CreateCommentRequestDto commentInfo) {
        User user = userRepository.findById(commentInfo.getUserId()).get();
        Article article = articleRepository.findById(commentInfo.getArticleId()).get();

        try {
            Comment comment = Comment.builder()
                    .content(commentInfo.getContent())
                    .user(user)
                    .article(article)
                    .build();
            Comment saveComment = commentRepository.save(comment);
            return saveComment.getId();
        } catch (Exception e) {
            throw e;
        }
    }

    // 댓글 수정
    @Transactional
    @Override
    public void updateComment(CreateCommentRequestDto commentInfo, Long commentId) {

        try {
            Comment comment = commentRepository.findByIdAndArticleId(commentId, commentInfo.getArticleId()).get();
            comment.updateContent(commentInfo.getContent());
        } catch (Exception e) {
            throw e;
        }
    }

    // 댓글 삭제
    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

}
