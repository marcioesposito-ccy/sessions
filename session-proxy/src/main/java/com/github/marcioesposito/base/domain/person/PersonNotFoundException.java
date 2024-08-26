package com.github.marcioesposito.base.domain.person;

import java.io.Serial;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {

  @Serial private static final long serialVersionUID = -1574309537077165154L;
}
