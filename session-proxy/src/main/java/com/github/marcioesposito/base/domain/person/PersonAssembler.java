package com.github.marcioesposito.base.domain.person;

import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
class PersonAssembler {

  public Person assemble(@NonNull final PersonData data, @NonNull final Integer number) {
    return Person.builder().name(data.getName()).number(number).build();
  }

  public Person assemble(@NonNull final Person entity, @NonNull final Integer number) {
    entity.setNumber(number);
    return entity;
  }
}
