package com.talkspace.account.infra.repository;

import com.talkspace.account.application.repository.AccountRepository;
import com.talkspace.account.domain.entity.Account;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepositoryMemory implements AccountRepository {
  private final List<Account> accounts;

  public AccountRepositoryMemory() {
    this.accounts = new ArrayList<>();
  }

  @Override
  public void save(Account account) {
    this.accounts.add(account);
  }

  @Override
  public Optional<Account> findByEmail(String email) {
    return this.accounts.stream().filter(account -> account.getEmail().equals(email)).findFirst();
  }
}
