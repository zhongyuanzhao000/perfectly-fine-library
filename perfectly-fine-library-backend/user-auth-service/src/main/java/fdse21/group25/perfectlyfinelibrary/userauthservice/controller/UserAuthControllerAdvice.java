package fdse21.group25.perfectlyfinelibrary.userauthservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fdse21.group25.perfectlyfinelibrary.common.dto.ErrorMessageDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;

@RestControllerAdvice
public class UserAuthControllerAdvice {
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto notFoundExceptionHandler(NotFoundException e) {
        return new ErrorMessageDto(e);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessageDto unauthorizedExcetionHandler(UnauthorizedException e) {
        return new ErrorMessageDto(e);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessageDto conflictExceptionHandler(ConflictException e) {
        return new ErrorMessageDto(e);
    }
}
