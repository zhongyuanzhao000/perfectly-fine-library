package fdse21.group25.perfectlyfinelibrary.common.component;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import fdse21.group25.perfectlyfinelibrary.common.dto.ErrorMessageDto;
import fdse21.group25.perfectlyfinelibrary.common.exception.BadRequestException;
import fdse21.group25.perfectlyfinelibrary.common.exception.ConflictException;
import fdse21.group25.perfectlyfinelibrary.common.exception.NotFoundException;
import fdse21.group25.perfectlyfinelibrary.common.exception.UnauthorizedException;
import feign.FeignException;
import feign.FeignException.BadRequest;
import feign.FeignException.Conflict;
import feign.FeignException.NotFound;
import feign.FeignException.Unauthorized;

@RestControllerAdvice
public class DefaultControllerAdvice {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto BadRequestExceptionHandler(BadRequestException e) {
        return new ErrorMessageDto(e);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessageDto ConflictExceptionHandler(ConflictException e) {
        return new ErrorMessageDto(e);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto NotFoundExceptionHandler(NotFoundException e) {
        return new ErrorMessageDto(e);
    }

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessageDto UnauthorizedExceptionHandler(UnauthorizedException e) {
        return new ErrorMessageDto(e);
    }

    @ExceptionHandler(FeignException.BadRequest.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessageDto FeignBadRequestExceptionHandler(BadRequest e) {
        return new ErrorMessageDto();
    }

    @ExceptionHandler(FeignException.Unauthorized.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorMessageDto FeignUnauthorizedExceptionHandler(Unauthorized e) {
        return new ErrorMessageDto();
    }

    @ExceptionHandler(FeignException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessageDto FeignNotFoundExceptionHandler(NotFound e) {
        return new ErrorMessageDto();
    }

    @ExceptionHandler(FeignException.Conflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorMessageDto FeignConflictExceptionHandler(Conflict e) {
        return new ErrorMessageDto();
    }
}
