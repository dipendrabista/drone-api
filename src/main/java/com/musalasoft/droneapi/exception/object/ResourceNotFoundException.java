package com.musalasoft.droneapi.exception.object;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class ResourceNotFoundException extends RuntimeException {
    public final String message;

    public static ResourceNotFoundException of(String message) {
        return new ResourceNotFoundException(message);
    }

}
