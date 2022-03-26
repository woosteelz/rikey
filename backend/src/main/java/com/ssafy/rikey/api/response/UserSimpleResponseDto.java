package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Area;
import com.ssafy.rikey.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@ApiModel("UserSimpleResponseDto")
public class UserSimpleResponseDto {

    @ApiModelProperty(value = "유저 Id", example = "1232354")
    private String id;

    @ApiModelProperty(value = "유저 지역", example = "서울")
    private Area area;

    @ApiModelProperty(value = "유저 닉네임", example = "nickname")
    private String nickName;

    public UserSimpleResponseDto(User user) {
        id = user.getId();
        area = user.getArea();
        nickName = user.getNickName();
    }
}
