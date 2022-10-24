package com.sistema.blog.exception;

import com.sistema.blog.dto.ErrorDetail;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorDetail> manejarResourceNotFoundException(
      ResourceNotFoundException exception, WebRequest webRequest) {

    ErrorDetail errorDetalle =
        new ErrorDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
    return new ResponseEntity<>(errorDetalle, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(BlogAppException.class)
  public ResponseEntity<ErrorDetail> manejarBlogAppException(
      BlogAppException exception, WebRequest webRequest) {

    ErrorDetail errorDetalle =
        new ErrorDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
    return new ResponseEntity<>(errorDetalle, HttpStatus.BAD_REQUEST);
  }


  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorDetail> manejarGlobalException(
      Exception exception, WebRequest webRequest) {

    ErrorDetail errorDetalle =
        new ErrorDetail(new Date(), exception.getMessage(), webRequest.getDescription(false));
    return new ResponseEntity<>(errorDetalle, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
    Map<String, String> errores = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String nombreCampo = ((FieldError)error).getField();
      String mensaje = error.getDefaultMessage();

      errores.put(nombreCampo, mensaje);
    });

    return new ResponseEntity<>(errores,HttpStatus.BAD_REQUEST);
  }

}
