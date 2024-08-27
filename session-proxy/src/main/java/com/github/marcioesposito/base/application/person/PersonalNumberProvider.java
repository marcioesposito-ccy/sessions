package com.github.marcioesposito.base.application.person;

public interface PersonalNumberProvider {

  int generateNumber();

  int changeNumber(int number);
}
