package com.github.marcioesposito.base.infrastructure.config.featureflag;

import static com.github.marcioesposito.base.infrastructure.config.featureflag.FeatureFlagConfiguration.PROPERTY_NAME;

import com.github.marcioesposito.base.application.featureflag.FeatureFlagManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(FeatureFlagProperties.class)
@ConditionalOnProperty(name = PROPERTY_NAME, havingValue = "true", matchIfMissing = true)
public class FeatureFlagConfiguration {

  static final String PROPERTY_NAME = "tcc.feature-flag.enabled";

  @Bean
  public FeatureFlagManager featureFlagManager(final FeatureFlagProperties properties) {
    return FeatureFlagManager.builder().flags(properties.getFlags()).build();
  }
}
