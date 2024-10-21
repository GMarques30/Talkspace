package com.talkspace.account.domain.entity;

import com.talkspace.account.domain.vo.Email;
import com.talkspace.account.domain.vo.Name;
import com.talkspace.account.domain.vo.Password;

import java.time.LocalDateTime;
import java.util.UUID;

public class Account {
  public final String accountId;
  private Name name;
  private Email email;
  private Password password;
  public final String createdAt;

  public Account(String firstName, String lastName, String email, String password) {
    this.accountId = UUID.randomUUID().toString();
    this.name = new Name(firstName, lastName);
    this.email = new Email(email);
    this.password = Password.create(password);
    this.createdAt = LocalDateTime.now().toString();
  }

  public Account(String accountId, String firstName, String lastName, String email, String password, String createdAt) {
    this.accountId = accountId;
    this.name = new Name(firstName, lastName);
    this.email = new Email(email);
    this.password = Password.restore(password);
    this.createdAt = createdAt;
  }

  public String getFirstName() {
    return this.name.getFirstName();
  }

  public String getLastName() {
    return this.name.getLastName();
  }

  public String getName() {
    return this.name.toString();
  }

  public String getEmail() {
    return this.email.toString();
  }

  public String getPassword() {
    return this.password.toString();
  }

  public boolean passwordMatches(String password) {
    return this.password.passwordMatches(password);
  }
}
