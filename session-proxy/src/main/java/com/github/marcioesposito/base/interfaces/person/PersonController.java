package com.github.marcioesposito.base.interfaces.person;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

import com.github.marcioesposito.base.application.person.PersonApplicationService;
import com.github.marcioesposito.base.domain.person.Person;
import com.github.marcioesposito.base.domain.person.PersonService;
import java.net.URI;
import java.util.List;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping(path = "/people")
@RequiredArgsConstructor
public class PersonController {

  @NonNull private final PersonService personService;

  @NonNull private final PersonApplicationService personApplicationService;

  @NonNull private final PersonResponseAssembler personResponseAssembler;

  @GetMapping
  public ResponseEntity<List<PersonResponse>> get() {
    final var entities = personService.findAll();
    final var responses = personResponseAssembler.assemble(entities);
    return ResponseEntity.ok(responses);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<PersonResponse> get(@PathVariable("id") final Integer id) {
    final var entity = personService.find(id);
    final var response = personResponseAssembler.assemble(entity);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<PersonResponse> post(@RequestBody final PersonRequest request) {
    final var entity = personApplicationService.create(request);
    final var response = personResponseAssembler.assemble(entity);
    return ResponseEntity.created(location(entity)).body(response);
  }

  @PutMapping(path = "/{id}:renumber")
  public ResponseEntity<PersonResponse> putRenumber(@PathVariable("id") final Integer id) {
    final var entity = personApplicationService.renumber(id);
    final var response = personResponseAssembler.assemble(entity);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<PersonResponse> delete(@PathVariable("id") final Integer id) {
    personService.delete(id);
    return ResponseEntity.noContent().build();
  }

  private URI location(final Person entity) {
    return MvcUriComponentsBuilder.fromMethodCall(on(PersonController.class).get(entity.getId()))
        .build()
        .toUri();
  }
}
