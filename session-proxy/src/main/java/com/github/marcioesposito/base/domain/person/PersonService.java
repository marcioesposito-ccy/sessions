package com.github.marcioesposito.base.domain.person;

import com.github.marcioesposito.base.domain.person.Person.UniqueKeys;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {

  @NonNull private final PersonAssembler assembler;

  @NonNull private final PersonRepository repository;

  public List<Person> findAll() {
    return repository.findAll();
  }

  public Person find(@NonNull final Integer id) {
    return repository.findById(id).orElseThrow(PersonNotFoundException::new);
  }

  public Person create(@NonNull final PersonData data, @NonNull final Integer number) {
    return saveAsUnique(assembler.assemble(data, number));
  }

  public Person renumber(@NonNull final Person entity, @NonNull final Integer number) {
    return repository.save(assembler.assemble(entity, number));
  }

  public void delete(@NonNull final Integer id) {
    repository.delete(find(id));
  }

  private Person saveAsUnique(final Person entity) {
    try {
      return repository.save(entity);
    } catch (Exception e) {
      if (e.getMessage().contains(UniqueKeys.UK1)) {
        throw new PersonConflictException(e);
      } else {
        throw e;
      }
    }
  }
}
