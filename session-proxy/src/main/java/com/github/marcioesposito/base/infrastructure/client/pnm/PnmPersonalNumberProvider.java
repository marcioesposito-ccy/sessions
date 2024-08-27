package com.github.marcioesposito.base.infrastructure.client.pnm;

import com.github.marcioesposito.base.application.person.PersonalNumberProvider;
import java.util.Random;
import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component("PNM")
public class PnmPersonalNumberProvider implements PersonalNumberProvider {

  @Override
  public Integer generateNumber() {
    return new Random().nextInt();
  }

  @Override
  public Integer changeNumber(@NonNull final Integer number) {
    return number + 1;
  }
}
