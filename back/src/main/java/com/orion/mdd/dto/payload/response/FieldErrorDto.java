package com.orion.mdd.dto.payload.response;

import lombok.Getter;

@Getter
public final class FieldErrorDto extends MessageDto {
    private final String fieldName;

    public FieldErrorDto(String fieldName, String errorMessage) {
        super(errorMessage);
        this.fieldName = fieldName;
    }
}