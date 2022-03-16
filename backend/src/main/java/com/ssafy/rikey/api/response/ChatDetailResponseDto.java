package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.ChatMessage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@ApiModel("ChatDetailResponseDto")
public class ChatDetailResponseDto {

    @ApiModelProperty(name = "채팅 메시지 id", example = "1")
    private Long chatMessageId;

    @ApiModelProperty(name = "채팅 작성자 닉네임", example = "yeongha")
    private String nickname;

    @ApiModelProperty(name = "채팅 작성자 프로필 이미지", example = "프로필 이미지")
    private String profilePic;

    @ApiModelProperty(name = "채팅 내용", example = "안녕하세요!")
    private String content;

    @ApiModelProperty(name = "채팅 작성일자", example = "2022-02-01-23:59:59")
    private LocalDateTime createdTime;

    public ChatDetailResponseDto(ChatMessage chatMessage) {
        chatMessageId = chatMessage.getId();
        nickname = chatMessage.getUser().getNickName();
        profilePic = chatMessage.getUser().getProfile_pic();
        content = chatMessage.getContent();
        createdTime = chatMessage.getCreatedTime();
    }
}
