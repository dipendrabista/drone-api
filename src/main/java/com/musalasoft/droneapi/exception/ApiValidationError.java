package com.musalasoft.droneapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author dipendra.bista
 * @version 1.0
 * @cteatedat 2020/05/21
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiValidationError extends ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    public ApiValidationError(String object, String message, Object rejectedValue, String s) {
        this.object = object;
        this.message = message;
        this.rejectedValue = rejectedValue;
        this.field = s;
    }

    public ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
