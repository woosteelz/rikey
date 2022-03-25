package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
@ApiModel("DeleteReviewRequestDto")
public class DeleteReviewRequestDto {

    @ApiModelProperty(value = "유저 아이디", example = "userId")
    private String userId;
}
