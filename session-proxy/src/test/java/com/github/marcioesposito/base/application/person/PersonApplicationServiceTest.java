package com.github.marcioesposito.base.application.person;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.github.marcioesposito.base.application.featureflag.FeatureFlagManager;
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

  @Mock private PersonalNumberProvider oldPersonalNumberProvider;

  @Mock private PersonalNumberProvider newPersonalNumberProvider;

  @Mock private FeatureFlagManager featureFlagManager;

  @BeforeEach
  void onBeforeEach() {
    subject =
        new PersonApplicationService(
            personService,
            oldPersonalNumberProvider,
            newPersonalNumberProvider,
            featureFlagManager);
  }

  // ----------------------- //
  // ----- CONSTRUCTOR ----- //
  // ----------------------- //

  @Test
  @SuppressWarnings("DataFlowIssue")
  void constructor_WhenPersonServiceIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable =
        () ->
            new PersonApplicationService(
                null, oldPersonalNumberProvider, newPersonalNumberProvider, featureFlagManager);
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  @SuppressWarnings("DataFlowIssue")
  void constructor_WhenOldPersonalNumberProviderIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable =
        () ->
            new PersonApplicationService(
                personService, null, newPersonalNumberProvider, featureFlagManager);
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  @SuppressWarnings("DataFlowIssue")
  void constructor_WhenNewPersonalNumberProviderIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable =
        () ->
            new PersonApplicationService(
                personService, oldPersonalNumberProvider, null, featureFlagManager);
    assertThrows(IllegalArgumentException.class, executable);
  }

  @Test
  @SuppressWarnings("DataFlowIssue")
  void constructor_WhenFeatureFlagManagerIsNull_ThenThrowsIllegalArgumentException() {
    final Executable executable =
        () ->
            new PersonApplicationService(
                personService, oldPersonalNumberProvider, newPersonalNumberProvider, null);
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
  void create_WhenFeatureFlagIsFalse_ThenInvokesTheOldMethodsAndReturnsAnEntity() {
    final var number = 1;
    when(featureFlagManager.isEnabled("personal-number-manager.enabled")).thenReturn(Boolean.FALSE);
    when(oldPersonalNumberProvider.generateNumber()).thenReturn(number);
    when(personService.create(personData, number)).thenReturn(savedPerson);

    final var actualEntity = subject.create(personData);
    assertThat(actualEntity).isSameAs(savedPerson);

    verifyNoMoreInteractions(featureFlagManager);
    verifyNoInteractions(savedPerson);
    verifyNoMoreInteractions(personService);
    verifyNoMoreInteractions(oldPersonalNumberProvider);
    verifyNoInteractions(newPersonalNumberProvider);
  }

  @Test
  void create_WhenFeatureFlagIsTrue_ThenInvokesTheNewMethodsAndReturnsAnEntity() {
    final var number = 1;
    when(featureFlagManager.isEnabled("personal-number-manager.enabled")).thenReturn(Boolean.TRUE);
    when(newPersonalNumberProvider.generateNumber()).thenReturn(number);
    when(personService.create(personData, number)).thenReturn(savedPerson);

    final var actualEntity = subject.create(personData);
    assertThat(actualEntity).isSameAs(savedPerson);

    verifyNoMoreInteractions(featureFlagManager);
    verifyNoInteractions(savedPerson);
    verifyNoMoreInteractions(personService);
    verifyNoInteractions(oldPersonalNumberProvider);
    verifyNoMoreInteractions(newPersonalNumberProvider);
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
  void renumber_WhenFeatureFlagIsFalse_ThenInvokesTheOldMethodsAndReturnsAnEntity() {
    final var id = 1;
    final var oldNumber = 10;
    final var newNumber = 20;

    when(featureFlagManager.isEnabled("personal-number-manager.enabled")).thenReturn(Boolean.FALSE);
    when(personService.find(id)).thenReturn(foundPerson);
    when(foundPerson.getNumber()).thenReturn(oldNumber);
    when(oldPersonalNumberProvider.changeNumber(oldNumber)).thenReturn(newNumber);
    when(personService.renumber(foundPerson, newNumber)).thenReturn(savedPerson);

    final var actualEntity = subject.renumber(id);
    assertThat(actualEntity).isSameAs(savedPerson);

    verifyNoMoreInteractions(featureFlagManager);
    verifyNoInteractions(savedPerson);
    verifyNoMoreInteractions(foundPerson);
    verifyNoMoreInteractions(personService);
    verifyNoMoreInteractions(oldPersonalNumberProvider);
    verifyNoInteractions(newPersonalNumberProvider);
  }

  @Test
  void renumber_WhenFeatureFlagIsTrue_ThenInvokesTheNewMethodsAndReturnsAnEntity() {
    final var id = 1;
    final var oldNumber = 10;
    final var newNumber = 20;

    when(featureFlagManager.isEnabled("personal-number-manager.enabled")).thenReturn(Boolean.TRUE);
    when(personService.find(id)).thenReturn(foundPerson);
    when(foundPerson.getNumber()).thenReturn(oldNumber);
    when(newPersonalNumberProvider.changeNumber(oldNumber)).thenReturn(newNumber);
    when(personService.renumber(foundPerson, newNumber)).thenReturn(savedPerson);

    final var actualEntity = subject.renumber(id);
    assertThat(actualEntity).isSameAs(savedPerson);

    verifyNoMoreInteractions(featureFlagManager);
    verifyNoInteractions(savedPerson);
    verifyNoMoreInteractions(foundPerson);
    verifyNoMoreInteractions(personService);
    verifyNoInteractions(oldPersonalNumberProvider);
    verifyNoMoreInteractions(newPersonalNumberProvider);
  }
}
