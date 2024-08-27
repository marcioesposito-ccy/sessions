package com.github.marcioesposito.base.infrastructure.config.featureflag;

import java.util.Map;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@ConfigurationProperties(prefix = "tcc.feature-flag")
public class FeatureFlagProperties {

  private final boolean enabled;

  private final Map<String, Boolean> flags;

  @ConstructorBinding
  FeatureFlagProperties(
      @DefaultValue("true") final boolean enabled, @DefaultValue final Map<String, Boolean> flags) {
    this.enabled = enabled;
    this.flags = flags;
  }
}
