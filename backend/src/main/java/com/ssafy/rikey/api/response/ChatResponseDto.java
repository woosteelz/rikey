package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.ChatMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ApiModel("ChatResponseDto")
public class ChatResponseDto {

    @ApiModelProperty(name = "채팅 id", example = "1")
    private Long chatId;

    @ApiModelProperty(name = "최근 채팅 작성자 닉네임", example = "yeongha")
    private String nickname;

    @ApiModelProperty(name = "최근 채팅 작성자 프로필 이미지", example = "이미지 경로")
    private String profilePic;

    @ApiModelProperty(name = "최근 채팅 메시지", example = "하이!")
    private String recentMsg;

    @ApiModelProperty(name = "최근 채팅 메시지 작성시간", example = "2022-02-01-23:59:59")
    private LocalDateTime createdTime;

    public ChatResponseDto(ChatMessage chatMessage) {
        chatId = chatMessage.getChat().getId();
        nickname = chatMessage.getUser().getNickName();
        profilePic = chatMessage.getUser().getProfile_pic();
        recentMsg = chatMessage.getContent();
        createdTime = chatMessage.getCreatedTime();
    }
}
