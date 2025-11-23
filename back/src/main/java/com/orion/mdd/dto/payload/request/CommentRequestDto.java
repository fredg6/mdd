package com.orion.mdd.dto.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentRequestDto {
    @NotNull
    private Long postId;
    @NotBlank
    private String content;
}