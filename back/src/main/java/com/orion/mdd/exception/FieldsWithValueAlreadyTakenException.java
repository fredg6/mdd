package com.orion.mdd.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FieldsWithValueAlreadyTakenException extends RuntimeException {
    private final List<String> fieldNames;
}