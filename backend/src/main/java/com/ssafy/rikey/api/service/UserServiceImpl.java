package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.UpdateUserRequestDto;
import com.ssafy.rikey.api.request.UserRequestDto;
import com.ssafy.rikey.api.response.*;
import com.ssafy.rikey.db.entity.*;
import com.ssafy.rikey.db.repository.AuthRepository;
import com.ssafy.rikey.db.repository.UserRepository;
import com.ssafy.rikey.util.HashEncoder;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;
    private final HashEncoder hashEncoder;
    private RestTemplate restTemplate;

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
        user.update(updateUserRequestDto.getNickName(), updateUserRequestDto.getGreeting(), Area.valueOf(updateUserRequestDto.getArea()), updateUserRequestDto.getProfilePic());
    }

    // 프로필 조회
    @Override
    public UserResponseDto getUserProfile(String nickName) {
        User user = userRepository.findByNickName(nickName);

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
        Auth auth = user.getAuth();
        userRepository.delete(user);
        authRepository.delete(auth);
    }

    // 칼로리, 거리, 시간으로 랭킹 조회
    @Override
    public List<Integer> getRankings(String nickname, String area) {
        List<Integer> rankingList = new ArrayList<>();
        User user = userRepository.findByNickName(nickname);

        List<User> usersByCalorie = userRepository.findAllByAreaOrderByCumulCalorieDesc(Area.valueOf(area));
        int rankingByCalorie = usersByCalorie.indexOf(user);
        rankingList.add(rankingByCalorie + 1);
        System.out.println("usersByCalorie" + usersByCalorie);
        System.out.println("rankingByCalorie" + rankingByCalorie);

        List<User> usersByDistance = userRepository.findAllByAreaOrderByCumulDistanceDesc(Area.valueOf(area));
        int rankingByDistance = usersByDistance.indexOf(user);
        rankingList.add(rankingByDistance + 1);

        List<User> usersByTime = userRepository.findAll3ByAreaOrderByCumulTimeDesc(Area.valueOf(area));
        int rankingsByTime = usersByTime.indexOf(user);
        rankingList.add(rankingsByTime + 1);

        return rankingList;
    }

    @Transactional
    @Override
    public String uploadImage(MultipartFile uploadFile) throws Exception {
        // 파일 정보
        String originFilename = uploadFile.getOriginalFilename(); //파일이름
        String extension = originFilename.substring(originFilename.length()-3); //확장자

        // 사진인지 체크
        if(!(extension.equals("jpg") || extension.equals("png")|| extension.equals("PNG")|| extension.equals("JPG"))){
            throw new FileUploadException("파일 확장자가 jpg나 png가 아닙니다.");
        }
        //파일이름 랜덤으로 만들기
        String url="/user/";
        String saveFileName = UUID.randomUUID().toString() + originFilename.substring(originFilename.lastIndexOf(".")); //랜덤이름+확장자
        String saveFileName2 = url+saveFileName;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        //파일 바이트
        ByteArrayResource fileAsResource = new ByteArrayResource(uploadFile.getBytes()){
            @Override
            public String getFilename() {
                return saveFileName2;
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("uploadFile", fileAsResource); //파일 바이트 저장
        body.add("parentPath","profile");

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        String serverUrl = "http://j6c208.p.ssafy.io:3000/upload";

        ResponseEntity<String> response = restTemplate
                .postForEntity(serverUrl, requestEntity, String.class);

        return "http://j6c208.p.ssafy.io/images"+saveFileName2;
    }
}
