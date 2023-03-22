package com.musalasoft.droneapi.util;

import org.springframework.validation.ObjectError;

import java.util.List;
import java.util.Objects;

public class ErrorObjectUtility {

    public static String getMessageFromErrorObject(List<ObjectError> errors) {
        StringBuilder errorMessage = new StringBuilder(Objects.requireNonNull(errors.get(0).getDefaultMessage()));

        if (errors.size() > 1) {
            for ( int i = 1; i < errors.size(); i++) {
                errorMessage
                        .append("\n")
                        .append(errors.get(i).getDefaultMessage());
            }
        }

        return errorMessage.toString();
    }
}
