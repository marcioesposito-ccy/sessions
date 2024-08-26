package com.github.marcioesposito.base.domain.person;

import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class PersonConflictException extends RuntimeException {

  @Serial private static final long serialVersionUID = 689694138400543079L;

  public PersonConflictException(final Throwable cause) {
    super(cause);
  }
}
