package com.talkspace.account.application.usecase;

import com.talkspace.account.application.dto.SigninInput;
import com.talkspace.account.application.dto.SigninOutput;
import com.talkspace.account.application.exception.InvalidCredentialsException;
import com.talkspace.account.application.repository.AccountRepository;
import com.talkspace.account.domain.entity.Account;
import com.talkspace.account.infra.repository.AccountRepositoryMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SigninTest {
    private AccountRepository accountRepository;
    @InjectMocks
    private Signin sut;

    @BeforeEach
    public void beforeEach() {
        accountRepository = new AccountRepositoryMemory();
        sut = new Signin(accountRepository);

        try {
            MockitoAnnotations.openMocks(this);
            var secretKeyField = Signin.class.getDeclaredField("secretKey");
            secretKeyField.setAccessible(true);
            secretKeyField.set(sut, "test-secret-key");
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Should be able to sign in with an existing account")
    public void should_be_able_to_sign_in_with_an_existing_account() {
        Account account = new Account("John", "Doe", "john.doe@example.com", "John@123");
        this.accountRepository.save(account);
        SigninOutput output = this.sut.execute(new SigninInput(account.getEmail(), "John@123"));
        assertNotNull(output);
    }

    @Test
    @DisplayName("Should not be able to sign in without an existing account")
    public void should_not_be_able_to_sign_in_without_an_existing_account() {
        assertThrows(InvalidCredentialsException.class, () -> this.sut.execute(new SigninInput("john.doe@example.com", "John@123")));
    }

    @Test
    @DisplayName("Should not be able to sign in with a password other than the one registered")
    public void should_not_be_able_to_sign_in_with_a_password_other_than_the_one_registered() {
        Account account = new Account("John", "Doe", "john.doe@example.com", "John@123");
        this.accountRepository.save(account);
        assertThrows(InvalidCredentialsException.class, () -> this.sut.execute(new SigninInput(account.getEmail(), "John@12345")));
    }
}
