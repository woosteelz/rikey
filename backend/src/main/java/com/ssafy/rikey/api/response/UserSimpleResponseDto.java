package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Area;
import com.ssafy.rikey.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("UserSimpleResponseDto")
public class UserSimpleResponseDto {

    @ApiModelProperty(value = "유저 Id", example = "1232354")
    private String id;

    @ApiModelProperty(value = "유저 지역", example = "서울")
    private Area area;

    @ApiModelProperty(value = "유저 닉네임", example = "nickname")
    private String nickName;

    @ApiModelProperty(value = "유저 키", example = "180")
    private int height;

    @ApiModelProperty(value = "유저 몸무게", example = "80")
    private int weight;

    public UserSimpleResponseDto(User user) {
        id = user.getId();
        area = user.getArea();
        nickName = user.getNickName();
        height = user.getHeight();
        weight = user.getWeight();
    }
}
