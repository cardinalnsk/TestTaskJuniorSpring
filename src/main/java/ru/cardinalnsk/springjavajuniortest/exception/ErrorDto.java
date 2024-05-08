package ru.cardinalnsk.springjavajuniortest.exception;

import java.util.List;
import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorDto(
    HttpStatus status,
    List<String> message
) {

}
