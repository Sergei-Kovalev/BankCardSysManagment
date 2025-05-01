package jdev.kovalev.BankCardSysManagment.exception.handler;

import jdev.kovalev.BankCardSysManagment.exception.CardNotFoundException;
import jdev.kovalev.BankCardSysManagment.exception.UserNotFoundException;
import jdev.kovalev.BankCardSysManagment.exception.WrongCardStatusException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.stream.Collectors;

@RestControllerAdvice
@RequiredArgsConstructor
public class AdminControllersExceptionHandler {

    @ExceptionHandler({
            CardNotFoundException.class,
            UserNotFoundException.class})
    public ResponseEntity<CustomErrorResponse> handleNotFoundException(Exception e, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, WebRequest request) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; ", "", "."));

        CustomErrorResponse errorResponse = new CustomErrorResponse(message, HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WrongCardStatusException.class)
    public ResponseEntity<CustomErrorResponse> handleWrongStatusException(Exception e, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<CustomErrorResponse> handleIllegalArgumentException(Exception e, WebRequest request) {
        CustomErrorResponse errorResponse = new CustomErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST, request);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
