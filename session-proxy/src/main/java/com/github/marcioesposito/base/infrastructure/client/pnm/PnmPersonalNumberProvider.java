package com.github.marcioesposito.base.infrastructure.client.pnm;

import com.github.marcioesposito.base.application.person.PersonalNumberProvider;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("PNM")
public class PnmPersonalNumberProvider implements PersonalNumberProvider {

  @Override
  public int generateNumber() {
    log.info("Generating the number");
    final var next = new Random().nextInt(0, 2000);
    log.info("Number generated [number={}]", next);

    return next;
  }

  @Override
  public int changeNumber(final int number) {
    log.info("Changing the number");
    final var next = number + 1;
    log.info("Number changed [from={}, to={}]", number, next);

    return next;
  }
}
