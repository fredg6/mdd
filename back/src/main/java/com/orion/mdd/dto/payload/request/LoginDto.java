package com.orion.mdd.dto.payload.request;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String emailOrUsername, @NotBlank String password) {
}