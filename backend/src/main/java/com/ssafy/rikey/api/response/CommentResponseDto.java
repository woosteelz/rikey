package com.ssafy.rikey.api.response;

import com.ssafy.rikey.db.entity.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ApiModel("CommentResponseDto")
public class CommentResponseDto {

    @ApiModelProperty(name="댓글 id", example="1")
    private Long commentId;

    @ApiModelProperty(name="댓글 내용", example="내용1")
    private String content;

    @ApiModelProperty(name="댓글 수정일", example="2022-02-01-23:59:59")
    private LocalDateTime updatedTime;

    @ApiModelProperty(value = "댓글 작성자 닉네임", example = "영하")
    private String author;

    public CommentResponseDto(Comment comment){
        commentId = comment.getId();
        content = comment.getContent();
        updatedTime = comment.getUpdatedTime();
        author = comment.getUser().getNickName();
    }
}
