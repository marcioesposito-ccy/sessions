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

  @NonNull private final PersonService personService;

  @NonNull private final PersonalNumberProvider personalNumberProvider;

  public Person create(@NonNull final PersonData data) {
    final var number = personalNumberProvider.generateNumber();
    return personService.create(data, number);
  }

  public Person renumber(@NonNull final Integer id) {
    final var entity = personService.find(id);
    final var number = personalNumberProvider.changeNumber(entity.getNumber());
    return personService.renumber(entity, number);
  }
}
