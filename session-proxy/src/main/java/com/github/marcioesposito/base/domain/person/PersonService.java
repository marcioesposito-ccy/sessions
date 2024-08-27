package com.github.marcioesposito.base.domain.person;

import com.github.marcioesposito.base.domain.person.Person.UniqueKeys;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

  @NonNull private final PersonAssembler personAssembler;

  @NonNull private final PersonRepository personRepository;

  public List<Person> findAll() {
    return personRepository.findAll();
  }

  public Person find(@NonNull final Integer id) {
    return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
  }

  public Person create(@NonNull final PersonData data, @NonNull final Integer number) {
    return saveAsUnique(personAssembler.assemble(data, number));
  }

  public Person renumber(@NonNull final Person entity, @NonNull final Integer number) {
    return personRepository.save(personAssembler.assemble(entity, number));
  }

  public void delete(@NonNull final Integer id) {
    personRepository.delete(find(id));
  }

  private Person saveAsUnique(final Person entity) {
    try {
      return personRepository.save(entity);
    } catch (Exception e) {
      if (e.getMessage().contains(UniqueKeys.UK1)) {
        throw new PersonConflictException(e);
      } else {
        throw e;
      }
    }
  }
}
