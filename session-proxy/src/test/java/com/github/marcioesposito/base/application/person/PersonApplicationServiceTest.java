package com.github.marcioesposito.base.application.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.github.marcioesposito.base.domain.person.Person;
import com.github.marcioesposito.base.domain.person.PersonData;
import com.github.marcioesposito.base.domain.person.PersonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PersonApplicationServiceTest {

  private PersonApplicationService subject;

  @Mock private Person foundPerson;

  @Mock private Person savedPerson;

  @Mock private PersonData personData;

  @Mock private PersonService personService;

  @Mock private PersonalNumberProvider personalNumberProvider;

  @BeforeEach
  void onBeforeEach() {
    subject = new PersonApplicationService(personService, personalNumberProvider);
  }

  // ----------------------- //
  // ----- CONSTRUCTOR ----- //
  // ----------------------- //

  @Test
  @SuppressWarnings("DataFlowIssue")
  void constructor_WhenPersonServiceIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable = () -> new PersonApplicationService(null, personalNumberProvider);
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  @SuppressWarnings("DataFlowIssue")
  void constructor_WhenPersonalNumberProviderIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable = () -> new PersonApplicationService(personService, null);
    assertThrows(IllegalArgumentException.class, executable);
  }

  // ------------------ //
  // ----- CREATE ----- //
  // ------------------ //

  @Test
  @SuppressWarnings("DataFlowIssue")
  void create_WhenPersonDataIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable = () -> subject.create(null);
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  void create_WhenEverythingIsOk_ThenInvokesTheExpectedMethodsAndReturnsAnEntity() {
    final var number = 1;
    when(personalNumberProvider.generateNumber()).thenReturn(number);
    when(personService.create(personData, number)).thenReturn(savedPerson);

    final var actualEntity = subject.create(personData);
    assertThat(actualEntity).isSameAs(savedPerson);

    verifyNoInteractions(savedPerson);
    verifyNoMoreInteractions(personService);
    verifyNoMoreInteractions(personalNumberProvider);
  }

  // -------------------- //
  // ----- RENUMBER ----- //
  // -------------------- //

  @Test
  @SuppressWarnings("DataFlowIssue")
  void renumber_WhenIdIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable = () -> subject.renumber(null);
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  void renumber_WhenEverythingIsOk_ThenInvokesTheExpectedMethodsAndReturnsAnEntity() {
    final var id = 1;
    final var oldNumber = 10;
    final var newNumber = 20;

    when(personService.find(id)).thenReturn(foundPerson);
    when(foundPerson.getNumber()).thenReturn(oldNumber);
    when(personalNumberProvider.changeNumber(oldNumber)).thenReturn(newNumber);
    when(personService.renumber(foundPerson, newNumber)).thenReturn(savedPerson);

    final var actualEntity = subject.renumber(id);
    assertThat(actualEntity).isSameAs(savedPerson);

    verifyNoInteractions(savedPerson);
    verifyNoMoreInteractions(foundPerson);
    verifyNoMoreInteractions(personService);
    verifyNoMoreInteractions(personalNumberProvider);
  }
}
