package sportsbetting.controller;

import sportsbetting.exceptions.BetIdMismatchException;
import sportsbetting.exceptions.BetNotFoundException;
import sportsbetting.exceptions.OutcomeIdMismatchException;
import sportsbetting.exceptions.OutcomeNotFoundException;
import sportsbetting.exceptions.SportEventIdMismatchException;
import sportsbetting.exceptions.SportEventNotFoundException;
import sportsbetting.exceptions.WagerIdMismatchException;
import sportsbetting.exceptions.WagerNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({SportEventNotFoundException.class,
            BetNotFoundException.class,
            OutcomeNotFoundException.class,
            WagerNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({SportEventIdMismatchException.class,
            BetIdMismatchException.class,
            OutcomeIdMismatchException.class,
            WagerIdMismatchException.class,
            ConstraintViolationException.class,
            DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleIdMismatchRequest(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getLocalizedMessage(),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }
}
