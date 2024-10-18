package com.talkspace.account.application.usecase;

import com.talkspace.account.application.dto.SignupInput;
import com.talkspace.account.application.exception.AccountAlreadyExistsException;
import com.talkspace.account.application.repository.AccountRepository;
import com.talkspace.account.domain.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class Signup {

  private final AccountRepository accountRepository;

  @Autowired
  public Signup(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  public void execute(SignupInput input) {
    Optional<Account> accountAlreadyExists = this.accountRepository.findByEmail(input.email());
    if(accountAlreadyExists.isPresent()) {
      throw new AccountAlreadyExistsException();
    }
    Account account = new Account(input.firstName(), input.lastName(), input.email(), input.password());
    this.accountRepository.save(account);
  }
}
