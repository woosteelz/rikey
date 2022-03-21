package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.UpdateUserRequestDto;
import com.ssafy.rikey.api.request.UserRequestDto;
import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.api.response.UserResponseDto;
import com.ssafy.rikey.api.response.UserSimpleResponseDto;
import com.ssafy.rikey.api.service.UserService;
import com.ssafy.rikey.db.entity.User;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Api(tags = "User", value = "게시글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ApiOperation(value = "회원가입", notes = "회원가입을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> register(
            @RequestBody @ApiParam(value = "유저 정보") UserRequestDto userRequestDto) throws Exception {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            UserSimpleResponseDto userSimpleResponseDto = userService.register(userRequestDto);
            result.put("profile", userSimpleResponseDto);
            httpStatus = HttpStatus.OK;
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping("/login")
    @ApiOperation(value = "로그인", notes = "유저 로그인을 시도한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<Map<String, Object>> login(
            @RequestBody @ApiParam(value = "네이버 발급 고유 일련번호") Map<String, String> body) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            UserSimpleResponseDto userSimpleResponseDto = userService.login(body.get("authId"));
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
            result.put("profile", userSimpleResponseDto);
        } catch (NoSuchElementException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "NO USER");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PutMapping
    @ApiOperation(value = "유저 회원정보 수정", notes = "유저 회원정보를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> updateUserProfile(
            @RequestBody @ApiParam(value = "유저 정보") UpdateUserRequestDto updateUserRequestDto) throws Exception {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            userService.updateUserProfile(updateUserRequestDto);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/{nickName}")
    @ApiOperation(value = "유저 프로필 조회", notes = "유저 프로필을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getUserProfile(
            @PathVariable @ApiParam(value = "유저 닉네임", required = true) String nickName) {

        Map<String, Object> result = new HashMap<>();
        UserResponseDto user = null;
        HttpStatus httpStatus = null;

        try {
            user = userService.getUserProfile(nickName);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (NoSuchElementException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "NO USER");
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        result.put("user", user);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @DeleteMapping
    @ApiOperation(value = "회원탈퇴", notes = "회원 탈퇴한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> deleteUser(
            @RequestBody @ApiParam(value = "네이버 발급 고유 일련번호") Map<String, String> body) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        try {
            userService.deleteUser(body.get("userId"));
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}
