package com.github.marcioesposito.base.application.person;

import com.github.marcioesposito.base.domain.person.Person;
import com.github.marcioesposito.base.domain.person.PersonData;
import com.github.marcioesposito.base.domain.person.PersonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonApplicationService {

  @NonNull private final PersonService service;

  @NonNull private final PersonalNumberProvider provider;

  public Person create(@NonNull final PersonData data) {
    final var number = provider.generateNumber();
    return service.create(data, number);
  }

  public Person renumber(@NonNull final Integer id) {
    final var entity = service.find(id);
    final var number = provider.changeNumber(entity.getNumber());
    return service.renumber(entity, number);
  }
}
