package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("CreateChatRequestDto")
public class CreateChatRequestDto {

    @ApiModelProperty(value = "유저 ID", example = "유저 ID")
    private String userId;

    @ApiModelProperty(value = "채팅 상대방 닉네임", example = "ssafykim")
    private String nickName;
}
