package com.github.marcioesposito.base.infrastructure.config.featureflag;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;

@Builder
@AllArgsConstructor
public class FeatureFlagManager {

  @NonNull @Singular private final Map<String, @NonNull Boolean> flags;

  public boolean isEnabled(@NonNull final String name) {
    return flags.getOrDefault(name, Boolean.FALSE);
  }
}
