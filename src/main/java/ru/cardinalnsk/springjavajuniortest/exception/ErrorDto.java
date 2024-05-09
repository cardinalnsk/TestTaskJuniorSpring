package ru.cardinalnsk.springjavajuniortest.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import org.springframework.http.HttpStatus;

public record ErrorDto(
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime time,
    HttpStatus status,
    List<String> message
) {

    @Builder
    public ErrorDto(HttpStatus status, List<String> message) {
        this(LocalDateTime.now(), status, message);
    }
}
