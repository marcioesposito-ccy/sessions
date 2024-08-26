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

  @NonNull private final PersonService service;

  @NonNull private final PersonApplicationService applicationService;

  @NonNull private final PersonResponseAssembler responseAssembler;

  @GetMapping
  public ResponseEntity<List<PersonResponse>> get() {
    final var entities = service.findAll();
    final var responses = responseAssembler.assemble(entities);
    return ResponseEntity.ok(responses);
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<PersonResponse> get(@PathVariable("id") final Integer id) {
    final var entity = service.find(id);
    final var response = responseAssembler.assemble(entity);
    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<PersonResponse> post(@RequestBody final PersonRequest request) {
    final var entity = applicationService.create(request);
    final var response = responseAssembler.assemble(entity);
    return ResponseEntity.created(location(entity)).body(response);
  }

  @PutMapping(path = "/{id}:renumber")
  public ResponseEntity<PersonResponse> putRenumber(@PathVariable("id") final Integer id) {
    final var entity = applicationService.renumber(id);
    final var response = responseAssembler.assemble(entity);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping(path = "/{id}")
  public ResponseEntity<PersonResponse> delete(@PathVariable("id") final Integer id) {
    service.delete(id);
    return ResponseEntity.noContent().build();
  }

  private URI location(final Person entity) {
    return MvcUriComponentsBuilder.fromMethodCall(on(PersonController.class).get(entity.getId()))
        .build()
        .toUri();
  }
}
