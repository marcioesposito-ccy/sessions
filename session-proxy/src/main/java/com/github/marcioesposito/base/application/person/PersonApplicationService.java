package com.github.marcioesposito.base.application.person;

import com.github.marcioesposito.base.application.featureflag.FeatureFlagManager;
import com.github.marcioesposito.base.domain.person.Person;
import com.github.marcioesposito.base.domain.person.PersonData;
import com.github.marcioesposito.base.domain.person.PersonService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonApplicationService {

  private static final String FEATURE_FLAG_NAME = "new-personal-number-provider.enabled";

  @NonNull private final PersonService personService;

  @NonNull
  @Qualifier("CRM")
  private final PersonalNumberProvider oldPersonalNumberProvider;

  @NonNull
  @Qualifier("PNM")
  private final PersonalNumberProvider newPersonalNumberProvider;

  @NonNull private final FeatureFlagManager featureFlagManager;

  public Person create(@NonNull final PersonData data) {
    final int number;
    if (featureFlagManager.isEnabled(FEATURE_FLAG_NAME)) {
      number = newPersonalNumberProvider.generateNumber();
    } else {
      number = oldPersonalNumberProvider.generateNumber();
    }
    return personService.create(data, number);
  }

  public Person renumber(@NonNull final Integer id) {
    final var entity = personService.find(id);
    final int number;
    if (featureFlagManager.isEnabled(FEATURE_FLAG_NAME)) {
      number = newPersonalNumberProvider.changeNumber(entity.getNumber());
    } else {
      number = oldPersonalNumberProvider.changeNumber(entity.getNumber());
    }
    return personService.renumber(entity, number);
  }
}
