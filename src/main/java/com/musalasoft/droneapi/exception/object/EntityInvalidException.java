package com.musalasoft.droneapi.exception.object;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@AllArgsConstructor
public class EntityInvalidException extends RuntimeException{
    //This class could be modified to have a list property,
    //holding multiple error objects,
    //each object corresponding to a single invalid property,
    //and such list could be used to generate multiple ApiError objects in Handler.
    private static final long serialVersionUID = 1L;

    //private List<String> errorMessages;

    /**
     * Don't let anyone else instantiate this class
     */
    private EntityInvalidException(String message) {
        super(message);
    }

    public EntityInvalidException(String message, String objectName) {
        this(String.format(message, objectName));
    }

    //public EntityInvalidException(List<ObjectError> errorList) {}
}
