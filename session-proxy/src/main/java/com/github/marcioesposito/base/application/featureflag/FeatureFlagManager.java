package com.github.marcioesposito.base.application.featureflag;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

@Builder
@AllArgsConstructor
public class FeatureFlagManager {

  private static final boolean DEFAULT_VALUE = Boolean.FALSE;

  @NonNull @Singular private final Map<String, @NonNull Boolean> flags;

  public boolean isEnabled(@NonNull final String name) {
    return flags.getOrDefault(name, DEFAULT_VALUE);
  }
}
