package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateChatRequestDto;
import com.ssafy.rikey.api.response.ChatDetailResponseDto;
import com.ssafy.rikey.api.response.ChatResponseDto;
import com.ssafy.rikey.api.service.ChatService;
import com.ssafy.rikey.db.entity.User;
import com.ssafy.rikey.db.entity.UserChat;
import com.ssafy.rikey.db.repository.UserChatRepository;
import com.ssafy.rikey.db.repository.UserRepository;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Api(tags = "Chat", value = "채팅 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final UserRepository userRepository;
    private final UserChatRepository userChatRepository;

    @GetMapping("/{userId}")
    @ApiOperation(value = "전체 채팅 조회", notes = "전체 채팅을 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 204, message = "메시지 미존재"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getchats(
            @PathVariable @ApiParam(value = "사용자 id", required = true) String userId) {

        Map<String, Object> result = new HashMap<>();
        List<ChatResponseDto> chatList = null;
        HttpStatus httpStatus = null;

        try {
            chatList = chatService.getChats(userId);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.NO_CONTENT;
            result.put("status", "NO MESSAGES");
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("chats", chatList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @PostMapping
    @ApiOperation(value = "채팅 생성", notes = "채팅을 생성한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 400, message = "두 유저 사이에 이미 생성된 채팅"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> createChat(
            @RequestBody @ApiParam(value = "채팅 생성정보", required = true) CreateChatRequestDto chatInfo) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;

        boolean flag = false;
        Long chatId = null;
        User user = userRepository.findById(chatInfo.getUserId()).get();
        User counterpart = userRepository.findByNickName(chatInfo.getNickName());
        List<UserChat> userUserChatList = userChatRepository.findAllByUser(user);
        List<UserChat> counterpartUserChatList = userChatRepository.findAllByUser(counterpart);

        for (UserChat userChat1 : userUserChatList) {
            for (UserChat userChat2 : counterpartUserChatList) {
                if (userChat1.getChat().getId().equals(userChat2.getChat().getId())) {
                    flag = true;
                    chatId = userChat1.getChat().getId();
                    break;
                }
            }
            if (flag) {
                break;
            }
        }

        if (flag) {
            httpStatus = HttpStatus.BAD_REQUEST;
            result.put("status", "BAD REQUEST");
            result.put("chatId", chatId);
        }
        else {
            try {
                chatService.createChat(chatInfo);
                httpStatus = HttpStatus.CREATED;
                result.put("status", "SUCCESS");
            } catch (RuntimeException e) {
                e.printStackTrace();
                httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
                result.put("status", "SERVER ERROR");
            }
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }

    @GetMapping("/detail/{chatId}")
    @ApiOperation(value = "채팅 상세 조회", notes = "채팅을 상세 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> getChat(
            @PathVariable @ApiParam(value = "채팅 id", required = true) Long chatId) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        List<ChatDetailResponseDto> chatDetailList = null;

        try {
            chatDetailList = chatService.getChat(chatId);
            httpStatus = HttpStatus.OK;
            result.put("status", "SUCCESS");
        } catch (RuntimeException e) {
            e.printStackTrace();
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        result.put("chatMessages", chatDetailList);

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}
