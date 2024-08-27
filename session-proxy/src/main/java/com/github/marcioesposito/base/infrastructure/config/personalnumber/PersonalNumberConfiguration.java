package com.github.marcioesposito.base.infrastructure.config.personalnumber;

import com.github.marcioesposito.base.application.person.PersonalNumberProvider;
import com.github.marcioesposito.base.infrastructure.client.crm.CrmPersonalNumberProvider;
import com.github.marcioesposito.base.infrastructure.client.pnm.PnmPersonalNumberProvider;
import com.github.marcioesposito.base.infrastructure.config.featureflag.FeatureFlagManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonalNumberConfiguration {

  @Bean
  public PersonalNumberProvider personalNumberProvider(
      final FeatureFlagManager featureFlagManager) {
    return new PersonalNumberProviderProxy(
        featureFlagManager, new CrmPersonalNumberProvider(), new PnmPersonalNumberProvider());
  }
}
