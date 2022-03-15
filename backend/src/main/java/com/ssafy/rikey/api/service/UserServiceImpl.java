package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.UpdateUserRequestDto;
import com.ssafy.rikey.api.request.UserRequestDto;
import com.ssafy.rikey.api.response.*;
import com.ssafy.rikey.db.entity.*;
import com.ssafy.rikey.db.repository.AuthRepository;
import com.ssafy.rikey.db.repository.UserRepository;
import com.ssafy.rikey.util.HashEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final HashEncoder hashEncoder;

    // 회원가입
    @Transactional
    @Override
    public UserSimpleResponseDto register(UserRequestDto userRequestDto) {
        Auth auth = new Auth(hashEncoder.encode(userRequestDto.getAuthId()), false, null);
        auth = authRepository.save(auth);

        User user = User.builder()
                .id(hashEncoder.encode(auth.getId()))
                .auth(auth)
                .nickName(userRequestDto.getNickName())
                .greeting(userRequestDto.getGreeting())
                .area(Area.valueOf(userRequestDto.getArea()))
                .build();
        user = userRepository.save(user);

        return new UserSimpleResponseDto(user);
    }

    // 로그인
    @Override
    public UserSimpleResponseDto login(String authId) {
        Auth auth = authRepository.getById(hashEncoder.encode(authId));
        User user = userRepository.findByAuth(auth);
        return user == null ? null : new UserSimpleResponseDto(user);
    }

    // 회원정보 수정
    @Transactional
    @Override
    public void updateUserProfile(UpdateUserRequestDto updateUserRequestDto) {
        User user = userRepository.getById(updateUserRequestDto.getUserId());
        user.update(updateUserRequestDto.getNickName(), updateUserRequestDto.getGreeting(), Area.valueOf(updateUserRequestDto.getArea()));
    }

    // 프로필 조회
    @Override
    public UserResponseDto getUserProfile(String userId) {
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

    // 회원 탈퇴
    @Transactional
    @Override
    public void deleteUser(String userId) {
        User user = userRepository.getById(userId);
        userRepository.delete(user);
    }
}
