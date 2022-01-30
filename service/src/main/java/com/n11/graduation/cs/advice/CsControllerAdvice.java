package com.n11.graduation.cs.advice;

import com.n11.graduation.cs.constant.ErrorMessage;
import com.n11.graduation.cs.dto.ErrorResponseDTO;
import com.n11.graduation.cs.exception.CsEntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.security.NoSuchAlgorithmException;

@ControllerAdvice
@Slf4j
public class CsControllerAdvice {
    @ExceptionHandler(value = CsEntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(CsEntityNotFoundException exception) {
        log.error(exception.getMessage());
        ErrorResponseDTO response = new ErrorResponseDTO(exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentNotValidException exception) {
        log.error(exception.getMessage());
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessage.INVALID_PARAMETER.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = NoSuchAlgorithmException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(NoSuchAlgorithmException exception) {
        log.error(exception.getMessage());
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessage.INVALID_PARAMETER.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(MethodArgumentTypeMismatchException exception) {
        log.error(exception.getMessage());
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessage.INVALID_PARAMETER.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(ConstraintViolationException exception) {
        log.error(exception.getMessage());
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessage.CONSTRAINT_VIOLATION.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(DataIntegrityViolationException exception) {
        log.error(exception.getMessage());
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessage.DUPLICATE_ENTRY.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception exception) {
        log.error(exception.getMessage());
        ErrorResponseDTO response = new ErrorResponseDTO(ErrorMessage.UNEXPECTED_ERROR.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
