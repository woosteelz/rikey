package com.ssafy.rikey.api.controller;

import com.ssafy.rikey.api.request.CreateChatMessageDto;
import com.ssafy.rikey.api.service.ChatMessageService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "ChatMessage", value = "채팅메시지 API")
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/chats")
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate template;

    @MessageMapping("/{chatId}")
    @ApiOperation(value = "채팅메시지 전송", notes = "채팅메시지를 전송한다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "성공"),
            @ApiResponse(code = 204, message = "채팅메시지 작성 오류"),
            @ApiResponse(code = 500, message = "서버 오류"),
    })
    public ResponseEntity<Map<String, Object>> createMessage(
            @PathVariable @ApiParam(value = "채팅 id", required = true) Long chatId,
            @RequestBody @ApiParam(value = "채팅메시지정보", required = true) CreateChatMessageDto chatMessageInfo) {

        Map<String, Object> result = new HashMap<>();
        HttpStatus httpStatus = null;
        String content = chatMessageInfo.getContent().trim();

        try {
            if (!content.equals("")) {
                chatMessageService.createChatMessage(chatMessageInfo, chatId);
                template.convertAndSend("/sub/chat/room/" + chatId, chatMessageInfo);
                httpStatus = HttpStatus.CREATED;
                result.put("status", "SUCCESS");
            } else {
                httpStatus = HttpStatus.NO_CONTENT;
                result.put("status", "NO CONTENT");
            }
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            result.put("status", "SERVER ERROR");
        }

        return new ResponseEntity<Map<String, Object>>(result, httpStatus);
    }
}
