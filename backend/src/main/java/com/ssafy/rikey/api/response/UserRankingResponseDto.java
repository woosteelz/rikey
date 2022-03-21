package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel("UserRankingResponseDto")
public class UserRankingResponseDto {

    @ApiModelProperty(value = "유저 닉네임", example = "nickname")
    private String nickName;

    public UserRankingResponseDto(User user) {
        nickName = user.getNickName();
    }
}
