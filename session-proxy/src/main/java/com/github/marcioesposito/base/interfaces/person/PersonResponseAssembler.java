package com.github.marcioesposito.base.interfaces.person;

import com.github.marcioesposito.base.domain.person.Person;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
class PersonResponseAssembler {

  public List<PersonResponse> assemble(@NonNull final List<Person> entities) {
    return entities.stream().map(this::assemble).toList();
  }

  public PersonResponse assemble(@NonNull final Person entity) {
    return PersonResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .number(entity.getNumber())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }
}
