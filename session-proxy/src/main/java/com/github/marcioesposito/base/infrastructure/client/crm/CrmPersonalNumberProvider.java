package com.github.marcioesposito.base.infrastructure.client.crm;

import com.github.marcioesposito.base.application.person.PersonalNumberProvider;
import java.util.Random;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CrmPersonalNumberProvider implements PersonalNumberProvider {

  @Override
  public Integer generateNumber() {
    return new Random().nextInt();
  }

  @Override
  public Integer changeNumber(@NonNull final Integer number) {
    final var next = new Random().nextInt();
    if (next == number) {
      return changeNumber(number);
    } else {
      return next;
    }
  }
}
