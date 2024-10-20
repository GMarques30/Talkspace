package com.talkspace.account.domain.vo;

import com.talkspace.account.application.exception.ValidationException;

public class Name {
  private final String firstName;
  private final String lastName;

  public Name(String firstName, String lastName) {
    if(!firstName.matches("[a-zA-z]+") || !lastName.matches("[a-zA-z]+")) throw new ValidationException("Invalid name");
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return this.firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  @Override
  public String toString() {
    return String.format("%s %s", this.firstName, this.lastName);
  }
}
