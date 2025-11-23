package com.orion.mdd.dto.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PostResponseDto extends BaseEntityDto {
    private String title;
    private String content;
    private String createdAt;
    private String createdBy;
    private String topicTitle;
    private List<CommentResponseDto> comments;
}