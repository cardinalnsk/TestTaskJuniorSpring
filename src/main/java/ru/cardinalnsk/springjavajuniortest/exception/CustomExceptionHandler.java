package ru.cardinalnsk.springjavajuniortest.exception;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDto ValidationException(MethodArgumentNotValidException e) {
        List<String> errorsMessage = e.getBindingResult().getFieldErrors().stream()
            .map(x -> String.join(": ", x.getField(), x.getDefaultMessage())).toList();

        return ErrorDto.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message(errorsMessage)
            .build();
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ErrorDto userAlreadyExistException(UserAlreadyExistException e) {
        return ErrorDto.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message(List.of(e.getMessage()))
            .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ErrorDto userNotFoundException(UsernameNotFoundException e) {
        return ErrorDto.builder()
            .status(HttpStatus.BAD_REQUEST)
            .message(List.of(e.getMessage()))
            .build();
    }


}
