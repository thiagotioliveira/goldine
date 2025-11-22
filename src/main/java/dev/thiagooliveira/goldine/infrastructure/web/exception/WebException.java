package dev.thiagooliveira.goldine.infrastructure.web.exception;

public class WebException extends RuntimeException {
  public WebException(String message) {
    super(message);
  }
}
