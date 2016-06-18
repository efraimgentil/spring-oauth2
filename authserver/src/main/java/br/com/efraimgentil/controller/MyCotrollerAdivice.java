package br.com.efraimgentil.controller;

import br.com.efraimgentil.model.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Created by efraimgentil<efraimgentil@gmail.com> on 16/06/16.
 */
@ControllerAdvice
public class MyCotrollerAdivice {

/*
  Nao captura exceptions do @PreAuthorize
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(value= HttpStatus.UNAUTHORIZED)
  @ResponseBody
  public ErrorMessage handleAccessDeniedException(AccessDeniedException ex) {
    return new ErrorMessage();
  }
*/

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(value= HttpStatus.UNAUTHORIZED)
  @ResponseBody
  protected ErrorMessage handleAccessDeniedException(AccessDeniedException ex) {
    return new ErrorMessage();
  }


}
