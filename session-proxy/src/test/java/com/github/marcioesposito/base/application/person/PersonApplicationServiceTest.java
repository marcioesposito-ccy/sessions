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

  @Mock private Person foundEntity;

  @Mock private Person savedEntity;

  @Mock private PersonData data;

  @Mock private PersonService service;

  @Mock private PersonalNumberProvider provider;

  @BeforeEach
  void onBeforeEach() {
    subject = new PersonApplicationService(service, provider);
  }

  // ----------------------- //
  // ----- CONSTRUCTOR ----- //
  // ----------------------- //

  @Test
  @SuppressWarnings("DataFlowIssue")
  void constructor_WhenPersonServiceIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable = () -> new PersonApplicationService(null, provider);
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  @SuppressWarnings("DataFlowIssue")
  void constructor_WhenPersonalNumberProviderIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable = () -> new PersonApplicationService(service, null);
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
    when(provider.generateNumber()).thenReturn(number);
    when(service.create(data, number)).thenReturn(savedEntity);

    final var actualEntity = subject.create(data);
    assertThat(actualEntity).isSameAs(savedEntity);

    verifyNoInteractions(savedEntity);
    verifyNoMoreInteractions(service);
    verifyNoMoreInteractions(provider);
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

    when(service.find(id)).thenReturn(foundEntity);
    when(foundEntity.getNumber()).thenReturn(oldNumber);
    when(provider.changeNumber(oldNumber)).thenReturn(newNumber);
    when(service.renumber(foundEntity, newNumber)).thenReturn(savedEntity);

    final var actualEntity = subject.renumber(id);
    assertThat(actualEntity).isSameAs(savedEntity);

    verifyNoInteractions(savedEntity);
    verifyNoMoreInteractions(foundEntity);
    verifyNoMoreInteractions(service);
    verifyNoMoreInteractions(provider);
  }
}
