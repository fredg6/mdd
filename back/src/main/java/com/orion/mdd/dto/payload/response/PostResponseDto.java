package com.orion.mdd.dto.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostResponseDto extends BaseEntityDto {
    private String title;
    private String content;
    private String createdAt;
    private String createdBy;
}