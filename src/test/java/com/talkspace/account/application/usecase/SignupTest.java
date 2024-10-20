package com.talkspace.account.application.usecase;

import com.talkspace.account.application.dto.SignupInput;
import com.talkspace.account.application.exception.AccountAlreadyExistsException;
import com.talkspace.account.application.repository.AccountRepository;
import com.talkspace.account.domain.entity.Account;
import com.talkspace.account.domain.vo.Password;
import com.talkspace.account.infra.repository.AccountRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class SignupTest {

  private AccountRepository accountRepository;
  private Signup sut;

  @BeforeEach
  public void beforeEach() {
    this.accountRepository = new AccountRepositoryMemory();
    this.sut = new Signup(accountRepository);
  }

  @Test
  @DisplayName("Should be able to register a non-existent account")
  public void should_be_able_to_register_a_non_existent_account() {
    SignupInput input = new SignupInput("John", "Doe", "john.doe@example.com", "John@123");
    this.sut.execute(input);
    Account account = this.accountRepository.findByEmail(input.email())
            .orElseThrow(() -> new AssertionError("Account should be present after signup"));
    assertAll("Checking that the account data has been persisted correctly",
            () -> assertThat(account.getFirstName()).isEqualTo("John"),
            () -> assertThat(account.getLastName()).isEqualTo("Doe"),
            () -> assertThat(account.getEmail()).isEqualTo("john.doe@example.com"),
            () -> assertThat(account.getPassword()).isEqualTo(Password.create("John@123").toString())
    );
  }

  @Test
  @DisplayName("Should not be able to create an existing account")
  public void should_not_be_able_to_create_an_existing_account() {
    Account account = new Account("John", "Doe", "john.doe@example.com", "John@123");
    this.accountRepository.save(account);
    assertThrows(AccountAlreadyExistsException.class, () -> this.sut.execute(new SignupInput("John", "Doe", "john.doe@example.com", "John@123")));
  }
}
