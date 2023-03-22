package com.musalasoft.droneapi.dto;

import com.musalasoft.droneapi.exception.ApiError;
import lombok.*;

import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseDTO {
    private Object data;
    private List<ApiError> errors;
    private Map<String, Object> meta;
}
