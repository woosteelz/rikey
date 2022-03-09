package com.ssafy.rikey.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("CreateCommentRequestDto")
@Data
public class CreateCommentRequestDto {

    @ApiModelProperty(value = "댓글 내용", example = "내용1", required = true)
    private String content;
}
