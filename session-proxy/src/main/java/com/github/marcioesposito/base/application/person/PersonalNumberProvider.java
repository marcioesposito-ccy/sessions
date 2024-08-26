package com.github.marcioesposito.base.application.person;

import lombok.NonNull;

public interface PersonalNumberProvider {

  Integer generateNumber();

  Integer changeNumber(@NonNull Integer number);
}
