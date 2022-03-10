package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.response.ArticleResponseDto;
import com.ssafy.rikey.api.response.UserResponseDto;
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

@Api(tags = "User", value = "게시글 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    @ApiOperation(value = "유저 프로필 조회", notes = "유저 프로필을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getArticles(
            @PathVariable @ApiParam(value = "유저 id", required = true) Long userId) {

        Map<String, Object> result = new HashMap<>();
        UserResponseDto user = null;
        HttpStatus httpStatus = null;

        try {
            user = userService.getUser(userId);
            httpStatus = HttpStatus.OK;
            result.put("success", true);
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("success", false);
        }

        result.put("user", user);
        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}
