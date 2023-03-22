package com.musalasoft.droneapi.exception;

import com.musalasoft.droneapi.dto.ResponseDTO;
import com.musalasoft.droneapi.exception.object.AlreadyExistException;
import com.musalasoft.droneapi.exception.object.EntityInvalidException;
import com.musalasoft.droneapi.exception.object.ResourceNotFoundException;
import com.musalasoft.droneapi.util.ErrorObjectUtility;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandlerMethods extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return buildResponseEntity(
                ApiError.builder()
                        .message(String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()))
                        .debugMessage(ex.getMessage())
                        .status(BAD_REQUEST)
                        .build(), NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        return buildResponseEntity(ApiError.builder().message(ex.getParameterName() + " parameter is missing").debugMessage(ex.getMessage()).build()
                , BAD_REQUEST);

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ApiError apiError = ApiError.builder()
                .message(
                        ErrorObjectUtility.getMessageFromErrorObject(ex.getAllErrors())
                )
                .timestamp(LocalDateTime.now())
                .status(status)
                .build();


        apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError, BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
                                                                      WebRequest request) {
        return buildResponseEntity(ApiError.builder()
                .message(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()))
                .debugMessage(ex.getMessage())
                .build(), BAD_REQUEST);
    }


    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ResponseDTO> handleEntityNotFound(
            EntityNotFoundException ex) {
        return buildErrorResponse(
                ResponseDTO.builder()
                        .data(Collections.EMPTY_LIST)
                        .meta(Collections.EMPTY_MAP)
                        .errors(Arrays.asList((ApiError.builder()
                                .timestamp(LocalDateTime.now())
                                .status(NOT_FOUND)
                                .message(ex.getMessage())
                                .build()))).build(), NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<ResponseDTO> handleConstraintViolation(
            ConstraintViolationException ex) {
        return buildErrorResponse(ResponseDTO.builder()
                .data(Collections.EMPTY_LIST)
                .meta(Collections.EMPTY_MAP)
                .errors(Arrays.asList((ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(NOT_FOUND)
                        .message("Validation Error")
                        .build()))).build(), INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        logger.info(servletWebRequest.getHttpMethod() + " to " + servletWebRequest.getRequest().getServletPath());
        String error = "Malformed JSON request";
        return buildResponseEntity(ApiError.builder().message(error).debugMessage(ex.getMessage()).build(), BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Error writing JSON output";
        return buildResponseEntity(ApiError.builder().message(error).debugMessage(ex.getMessage()).build(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
                                                                  WebRequest request) {
        if (ex.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(ApiError.builder()
                    .message("Database error" + ex.getMessage()).build(), CONFLICT);
        }
        return buildResponseEntity(ApiError.builder().message(ex.getMessage()).build(), INTERNAL_SERVER_ERROR);
    }


    private ResponseEntity<Object> buildResponseEntity(ApiError apiError, HttpStatus status) {
        return new ResponseEntity<>(ResponseDTO.builder()
                .meta(Collections.EMPTY_MAP)
                .data(Collections.EMPTY_LIST)
                .errors(Arrays.asList((apiError))).build(), status);
    }

    private ResponseEntity<ResponseDTO> buildErrorResponse(ResponseDTO responseDTO, HttpStatus status) {
        return new ResponseEntity<>(responseDTO, status);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> resourceNotFoundException(Exception ex, WebRequest request) {
        System.out.println("" + ex.getMessage() + ex.getLocalizedMessage());
        return buildErrorResponse(ResponseDTO.builder()
                .data(Collections.EMPTY_LIST)
                .meta(Collections.EMPTY_MAP)
                .errors(Collections.singletonList((ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(NOT_FOUND)
                        .message(ex.getLocalizedMessage())
                        //.debugMessage(ExceptionUtils.getStackTrace(ex))
                        .build()))).build(), NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ResponseDTO> alreadyExistException(AlreadyExistException ex, WebRequest request) {
        return buildErrorResponse(ResponseDTO.builder()
                .data(Collections.EMPTY_LIST)
                .errors(Collections.singletonList((ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(INTERNAL_SERVER_ERROR)
                        .message(ex.getMessage())
                        .debugMessage("Already exist")
                        .build()))).build(), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityInvalidException.class)
    public ResponseEntity<ResponseDTO> entityInvalidException(EntityInvalidException ex, WebRequest request) {
        //The ResponseDTO could be built with multiple ApiError objects,
        //each object corresponding to a single invalid property.
        return buildErrorResponse(ResponseDTO.builder()
                .data(Collections.EMPTY_LIST)
                .errors(Collections.singletonList((ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(BAD_REQUEST)
                        .message(ex.getMessage())
                        .debugMessage("Entity Invalid")
                        .build()))).build(), BAD_REQUEST);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> genericException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return buildErrorResponse(ResponseDTO.builder()
                .data(Collections.EMPTY_LIST)
                .errors(Collections.singletonList((ApiError.builder()
                        .timestamp(LocalDateTime.now())
                        .status(INTERNAL_SERVER_ERROR)
                        .message(ex.getMessage())
                        .debugMessage("Test debug message")
                        .build()))).build(), INTERNAL_SERVER_ERROR);
    }


}
