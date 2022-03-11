package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.CreateCommentRequestDto;
import com.ssafy.rikey.api.response.CommentResponseDto;
import com.ssafy.rikey.db.entity.Article;
import com.ssafy.rikey.db.entity.Comment;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.ArticleRepository;
import com.ssafy.rikey.db.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    // 댓글 등록
    @Override
    public void createComment(CreateCommentRequestDto commentInfo, Article article, User user) {

        try {
            Comment comment = Comment.builder()
                    .content(commentInfo.getContent())
                    .user(user)
                    .article(article)
                    .build();
            commentRepository.save(comment);

        } catch (Exception e) {
            throw e;
        }
    }

    // 댓글 수정
    @Override
    public Comment updateComment(CreateCommentRequestDto commentInfo, Long commentId, Long articleId) {

        try {
            Comment comment = commentRepository.findByIdAndArticleId(commentId, articleId).get();
            comment.updateContent(commentInfo.getContent());
            commentRepository.save(comment);
            return comment;
        } catch (Exception e) {
            throw e;
        }
    }

    // 댓글 삭제
    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    // 게시글 내 댓글 리스트 조회
    @Override
    public List<CommentResponseDto> getCommentList(Long articleId) {
        Article article = articleRepository.findById(articleId).get();
        List<Comment> comments = commentRepository.findByArticle(article);
        return comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());
    }
}
