package com.github.marcioesposito.base.infrastructure.client.crm;

import com.github.marcioesposito.base.application.person.PersonalNumberProvider;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CrmPersonalNumberProvider implements PersonalNumberProvider {

  @Override
  public int generateNumber() {
    log.info("Generating the number");
    final var next = new Random().nextInt(0, 1000);
    log.info("Number generated [number={}]", next);

    return next;
  }

  @Override
  public int changeNumber(final int number) {
    log.info("Changing the number");
    var next = generateNumber();
    if (next == number) next = changeNumber(number);
    log.info("Number changed [from={}, to={}]", number, next);

    return next;
  }
}
