package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.api.response.RidingInfoResponseDto;
import com.ssafy.rikey.api.response.UserResponseDto;
import com.ssafy.rikey.db.entity.*;
import com.ssafy.rikey.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    // 프로필 조회
    @Override
    public UserResponseDto getUser(Long userId) {
        User user = userRepository.findById(userId).get();

        List<Article> articles = user.getArticles();
        List<ArticleResponseDto> articleResponseDtos = articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());

        List<Comment> comments = user.getComments();
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());

        List<Review> reviews = user.getReviews();
        List<ReviewResponseDto> reviewResponseDtos = reviews.stream().map(ReviewResponseDto::new).collect(Collectors.toList());

        List<RidingInfo> ridingInfos = user.getRidingInfos();
        List<RidingInfoResponseDto> ridingInfoResponseDtos = ridingInfos.stream().map(RidingInfoResponseDto::new).collect(Collectors.toList());

        return new UserResponseDto(user, articleResponseDtos, commentResponseDtos, reviewResponseDtos, ridingInfoResponseDtos);
    }
}
