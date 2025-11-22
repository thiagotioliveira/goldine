package dev.thiagooliveira.goldine.application.exception;

import dev.thiagooliveira.goldine.domain.exception.RootException;

public class ApplicationException extends RootException {

  public ApplicationException(String message) {
    super(message);
  }
}
