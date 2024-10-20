package com.talkspace.account.domain.entity;

import com.talkspace.account.domain.vo.Password;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AccountTest {
    private Account account;

    @BeforeEach
    public void beforeEach() {
        account = new Account("John", "Doe", "john.doe@example.com", "John@123");
    }

    @Test
    @DisplayName("Should be able to create a account entity")
    public void should_be_able_to_create_a_account_entity() {
        assertNotNull(account.accountId);
        assertThat(account.getFirstName()).isEqualTo("John");
        assertThat(account.getLastName()).isEqualTo("Doe");
        assertThat(account.getEmail()).isEqualTo("john.doe@example.com");
        assertThat(account.getPassword()).isEqualTo(Password.create("John@123").toString());
        assertNotNull(account.createdAt);
    }

    @Test
    @DisplayName("Should be able to return a full name")
    public void should_be_able_to_return_a_full_name() {
        assertThat(account.getName()).isEqualTo("John Doe");
    }

    @Test
    @DisplayName("Should be able to compare a whether the password are the same as the one entered")
    public void should_be_able_to_compare_a_whether_the_password_are_the_same_as_the_one_entered() {
        assertThat(account.passwordMatches("John@123")).isTrue();
        assertThat(account.passwordMatches("John@12345")).isFalse();
    }
}
