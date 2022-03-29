package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("CreateChatMessageDto")
public class CreateChatMessageDto {

    @ApiModelProperty(value = "유저 ID", example = "유저 ID")
    private String userId;

    @ApiModelProperty(value = "채팅메시지 내용", example = "안녕하세요!")
    private String content;
}
