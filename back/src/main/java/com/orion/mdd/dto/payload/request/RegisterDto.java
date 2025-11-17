package com.orion.mdd.dto.payload.request;

import jakarta.validation.constraints.*;

public record RegisterDto(
        @NotEmpty @Email String email,
        @NotBlank String username,
        @NotNull @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_CONSTRAINT_MESSAGE) String password) {

    private static final String PASSWORD_PATTERN = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";
    private static final String PASSWORD_CONSTRAINT_MESSAGE = """
            doit contenir au moins 8 caractères dont :
            - 1 chiffre
            - 1 lettre minuscule
            - 1 lettre majuscule
            - 1 caractère spécial (parmi #?!@$%^&*-)""";
}