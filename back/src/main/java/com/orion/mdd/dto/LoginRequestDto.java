package com.orion.mdd.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(@NotBlank String emailOrUsername, @NotBlank String password) {
}