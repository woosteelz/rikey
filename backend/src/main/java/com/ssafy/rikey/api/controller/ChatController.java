package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.response.ChatDetailResponseDto;
import com.ssafy.rikey.api.response.ChatResponseDto;
import com.ssafy.rikey.api.service.ChatService;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.repository.UserRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "Chat", value = "채팅 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;

    @GetMapping("/{userId}")
    @ApiOperation(value = "전체 채팅 조회", notes = "전체 채팅을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getchats(
            @PathVariable @ApiParam(value = "사용자 id", required = true) String userId) {

        Map<String, Object> result = new HashMap<>();
        List<ChatResponseDto> chatList = null;
        HttpStatus httpStatus = null;

        try {
            User user = userRepository.findById(userId).get();
            chatList = chatService.getChats(user);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("chats", chatList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping
    @ApiOperation(value = "채팅 상세 조회", notes = "채팅을 상세 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "채팅 탐색 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getchat(
            @PathVariable @ApiParam(value = "채팅 id", required = true) Long chatId) {

        Map<String, Object> result = new HashMap<>();
        List<ChatDetailResponseDto> chatDetailList = null;
        HttpStatus httpStatus = null;

    }
}
