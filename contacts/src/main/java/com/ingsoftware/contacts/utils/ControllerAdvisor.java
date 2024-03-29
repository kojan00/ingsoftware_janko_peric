package com.ingsoftware.contacts.utils;

import com.ingsoftware.contacts.exceptions.ContactNotFoundException;
import com.ingsoftware.contacts.exceptions.ContactTypeNotFoundException;
import com.ingsoftware.contacts.exceptions.NoDataFoundException;
import com.ingsoftware.contacts.exceptions.UserNotFoundException;

import jakarta.mail.MessagingException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<Object> handleUserNotFoundException(
      UserNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "User not found");

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(LockedException.class)
  public ResponseEntity<Object> handleLockedUser(LockedException e) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Account is not verified");

    return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(NoDataFoundException.class)
  public ResponseEntity<Object> handleNodataFoundException(
      NoDataFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "No data found");

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ContactNotFoundException.class)
  public ResponseEntity<Object> handleContactNotFoundException(
      ContactNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Contact not found");

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MessagingException.class)
  public ResponseEntity<Object> handleMessagingException(
          ContactNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "There is a problem with sending email");

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ContactTypeNotFoundException.class)
  public ResponseEntity<Object> handleContactTypeNotFoundException(
      ContactTypeNotFoundException ex, WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Contact type not found");

    return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolationException(
      ConstraintViolationException ex, WebRequest request) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDateTime.now());
    body.put("message", "Your input is not valid.");

    ex.printStackTrace();
    return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  // MISLIM DA OVAJ METOD NE HVATA LEPO IZ NEKOG RAZLOGA PA SAM ODRADIO
  // ContstraintViolationException IZNAD
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    Map<String, Object> body = new LinkedHashMap<>();
    body.put("timestamp", LocalDate.now());
    body.put("status", status.value());

    List<String> errors =
        ex.getBindingResult().getFieldErrors().stream()
            .map(x -> x.getDefaultMessage())
            .collect(Collectors.toList());

    body.put("errors", errors);

    return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
  }
}
