package dev.thiagooliveira.goldine.infrastructure.web;

import dev.thiagooliveira.goldine.infrastructure.web.exception.BusinessNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(BusinessNotFoundException.class)
  public String handle404(BusinessNotFoundException ex, Model model) {
    return "404";
  }
}
