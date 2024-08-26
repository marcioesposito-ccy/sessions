package com.github.marcioesposito.base.interfaces.person;

import com.github.marcioesposito.base.domain.person.PersonData;
import java.io.Serial;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class PersonRequest implements PersonData, Serializable {

  @Serial private static final long serialVersionUID = -6986477337645180194L;

  private String name;
}
