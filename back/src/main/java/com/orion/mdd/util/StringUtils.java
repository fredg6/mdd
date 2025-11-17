package com.orion.mdd.util;

import org.apache.commons.validator.routines.EmailValidator;

public class StringUtils {
    public static boolean isEmail(String str) {
        return EmailValidator.getInstance().isValid(str);
    }
}