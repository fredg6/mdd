package com.orion.mdd.dto.payload.response;

import lombok.Getter;

@Getter
public class TokensDto extends JwtDto {
    private final String refreshToken;

    public TokensDto(String jwt, String refreshToken) {
        super(jwt);
        this.refreshToken = refreshToken;
    }
}