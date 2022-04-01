package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel("LikeyRequestDto")
public class LikeyRequestDto {

    @ApiModelProperty(value = "게시글 id", example = "1")
    private Long articleId;

    @ApiModelProperty(value = "유저 id", example = "adsfsdf")
    private String userId;
}
