package com.github.marcioesposito.base.infrastructure.config.personalnumber;

import com.github.marcioesposito.base.application.person.PersonalNumberProvider;
import com.github.marcioesposito.base.infrastructure.config.featureflag.FeatureFlagManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PersonalNumberProviderProxy implements PersonalNumberProvider {

  private static final String FEATURE_FLAG_NAME = "new-personal-number-provider.enabled";

  @NonNull private final FeatureFlagManager featureFlagManager;

  @NonNull private final PersonalNumberProvider oldPersonalNumberProvider;

  @NonNull private final PersonalNumberProvider newPersonalNumberProvider;

  @Override
  public int generateNumber() {
    if (featureFlagManager.isEnabled(FEATURE_FLAG_NAME)) {
      return newPersonalNumberProvider.generateNumber();
    } else {
      return oldPersonalNumberProvider.generateNumber();
    }
  }

  @Override
  public int changeNumber(final int number) {
    if (featureFlagManager.isEnabled(FEATURE_FLAG_NAME)) {
      return newPersonalNumberProvider.changeNumber(number);
    } else {
      return oldPersonalNumberProvider.changeNumber(number);
    }
  }
}
