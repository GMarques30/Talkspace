package com.talkspace.account.application.repository;

import com.talkspace.account.domain.entity.Account;

import java.util.Optional;

public interface AccountRepository {
  void save(Account account);
  Optional<Account> findByEmail(String email);
}
