package am.itspace.sweetbakerystorecommon.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum Error {

    ENTITY_NOT_FOUND(404, HttpStatus.NOT_FOUND, "Entity not found");

    private final Integer code;
    private final HttpStatus httpStatus;
    private final String message;
}

