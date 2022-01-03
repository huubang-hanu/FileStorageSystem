package com.hanu.filestorage.exception;

import com.hanu.filestorage.dto.ErrorResponse;
import org.apache.catalina.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;
import java.time.LocalDateTime;


@ControllerAdvice
public class ExceptionHelper {
    private final Logger logger = LoggerFactory.getLogger(ExceptionHelper.class);

    /**
     * Handle ResourceNotFoundException
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { ResourceNotFoundException.class })
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        logger.error("Resource Not Found Exception: ",ex.getMessage());
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(LocalDateTime.now());
        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle exception when store file
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { StoreFileException.class })
    public ResponseEntity<ErrorResponse> handleStoreFileException(StoreFileException ex) {
        logger.error("Store File Exception: ",ex.getMessage());
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle IOException
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { IOException.class })
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        logger.error("Store File Exception ",ex.getMessage());
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle file validation exception
     * @param ex
     * @return
     */
    @ExceptionHandler(value = { InvalidFileException.class })
    public ResponseEntity<ErrorResponse> handleInvalidFileException(InvalidFileException ex) {
        logger.error("Store File Exception ",ex.getMessage());
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setMessage(ex.getMessage());
        error.setTimeStamp(LocalDateTime.now());

        return new ResponseEntity<ErrorResponse>(error, HttpStatus.BAD_REQUEST);
    }


}
