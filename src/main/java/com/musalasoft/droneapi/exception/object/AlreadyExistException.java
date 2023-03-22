package com.musalasoft.droneapi.exception.object;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
@Data
public class AlreadyExistException extends RuntimeException {
    public final String message;

    public static AlreadyExistException of(String message) {
        return new AlreadyExistException(message);
    }

}
