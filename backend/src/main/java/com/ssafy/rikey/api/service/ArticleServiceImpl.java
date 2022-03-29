package com.ssafy.rikey.api.service;

import com.ssafy.rikey.api.request.ArticleRequestDto;
import com.ssafy.rikey.api.response.ArticleDetailResponseDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.api.response.CommentResponseDto;
import com.ssafy.rikey.db.entity.*;
import com.ssafy.rikey.db.repository.ArticleRepository;
import com.ssafy.rikey.db.repository.CommentRepository;
import com.ssafy.rikey.db.repository.LikeyRepository;
import com.ssafy.rikey.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final LikeyRepository likeyRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Autowired
    private RestTemplate restTemplate;

    // 최근 게시글 조회
    @Override
    public List<ArticleResponseDto> getRecentArticles() {
        List<Article> articles = articleRepository.findTop5ByOrderByIdDesc();
        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    // 전체 게시글 조회
    @Override
    public List<ArticleResponseDto> getArticles(String category) {
        List<Article> articles = null;

        if (category.equals("ALL")) {
            articles = articleRepository.findRecentlyOrderByHIts();
            articles.addAll(articleRepository.findAllByOrderByIdDesc());
        } else {
            articles = articleRepository.findRecentlyByCategoryOrderByHIts(category);
            articles.addAll(articleRepository.findByCategoryOrderByIdDesc(Category.valueOf(category)));
        }

        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    // 게시글 상세 조회
    @Transactional
    @Override
    public ArticleDetailResponseDto getArticle(String nickName, Long articleId) {
        Article article = articleRepository.findById(articleId).get();
        List<Likey> likeys = likeyRepository.findByArticle(article);
        User user = userRepository.findByNickName(nickName);

        Boolean isLike = false;

        for (Likey likey : likeys) {
            if (likey.getUser().getId().equals(user.getId())) {
                isLike = true;
                break;
            }
        }

        article.increaseHits();
        List<Comment> comments = commentRepository.findByArticle(article);
        List<CommentResponseDto> commentResponseDtos = comments.stream().map(CommentResponseDto::new).collect(Collectors.toList());

        return new ArticleDetailResponseDto(isLike, article, commentResponseDtos);
    }

    // 내 게시글 조회
    @Override
    public List<ArticleResponseDto> getMyArticles(String nickname) {
        User user = userRepository.findByNickName(nickname);
        List<Article> articles = articleRepository.findByAuthorOrderByIdDesc(user);
        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    // 게시글 검색
    @Override
    public List<ArticleResponseDto> searchArticles(String keyword) {
        List<Article> articles = articleRepository.findByTitleContainingOrContentContainingOrderByIdDesc(keyword, keyword);
        return articles.stream().map(ArticleResponseDto::new).collect(Collectors.toList());
    }

    // 게시글 등록
    @Transactional
    @Override
    public Long createArticle(ArticleRequestDto articleRequestDto) {
        User user = userRepository.findById(articleRequestDto.getUserId()).get();

        Article article = Article.builder()
                .title(articleRequestDto.getTitle())
                .content(articleRequestDto.getContent())
                .pics(articleRequestDto.getPics())
                .category(Category.valueOf(articleRequestDto.getCategory()))
                .user(user)
                .build();

        Article saveArticle = articleRepository.save(article);
        return saveArticle.getId();
    }

    //사진 업로드
    @Override
    public List<String> uploadImage(List<MultipartFile> uploadFiles) throws Exception {
        List<String> urls = new ArrayList<>();

        for (MultipartFile uploadFile : uploadFiles) {

            // 파일 정보
            String originFilename = uploadFile.getOriginalFilename(); //파일이름
            String extension = originFilename.substring(originFilename.length()-3); //확장자

            // 사진인지 체크
            if(!(extension.equals("jpg") || extension.equals("png")|| extension.equals("PNG")|| extension.equals("JPG"))){
                throw new FileUploadException("파일 확장자가 jpg나 png가 아닙니다.");
            }
            //파일이름 랜덤으로 만들기
            String url="/article/";
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
            body.add("parentPath","article");

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            String serverUrl = "http://j6c208.p.ssafy.io:3000/upload";

            ResponseEntity<String> response = restTemplate
                    .postForEntity(serverUrl, requestEntity, String.class);

            String newUrl = "http://j6c208.p.ssafy.io/images"+saveFileName2;
            urls.add(newUrl);
        }

        return urls;
    }

    // 게시글 수정
    @Transactional
    @Override
    public void updateArticle(Long articleId, ArticleRequestDto articleRequestDto) {
        Article article = articleRepository.findById(articleId).get();
        article.update(articleRequestDto.getTitle(), articleRequestDto.getContent(), Category.valueOf(articleRequestDto.getCategory()));
    }

    // 게시글 삭제
    @Transactional
    @Override
    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }
}
