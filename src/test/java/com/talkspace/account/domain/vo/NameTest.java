package com.talkspace.account.domain.vo;

import com.talkspace.account.application.exception.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NameTest {
  @Test
  @DisplayName("Should be able to create a valid name")
  public void should_be_able_to_create_a_valid_name() {
    assertDoesNotThrow(() -> new Name("John", "Doe"));
    assertDoesNotThrow(() -> new Name("Fernando", "Silva"));
    assertDoesNotThrow(() -> new Name("Maria", "Campos"));
  }

  @Test
  @DisplayName("Should not be able to create a invalid names")
  void should_not_be_able_to_create_a_invalid_names() {
    assertThrows(ValidationException.class, () -> new Name("", "Silva"));
    assertThrows(ValidationException.class, () -> new Name("Fernando", ""));
    assertThrows(ValidationException.class, () -> new Name("Fernando123", "Silva"));
    assertThrows(ValidationException.class, () -> new Name("Fernando", "Si1va"));
    assertThrows(ValidationException.class, () -> new Name("   ", "Silva"));
    assertThrows(ValidationException.class, () -> new Name("Fernando", "   "));
  }

  @Test
  @DisplayName("Should be able to return a string with first name")
  public void should_be_able_to_return_a_string_with_first_name() {
    Name name = new Name("John", "Doe");
    assertThat(name.getFirstName()).isEqualTo("John");
  }

  @Test
  @DisplayName("Should be able to return a string with last name")
  public void should_be_able_to_return_a_string_with_last_name() {
    Name name = new Name("John", "Doe");
    assertThat(name.getLastName()).isEqualTo("Doe");
  }

  @Test
  @DisplayName("Should be able to return a string with full name")
  public void should_be_able_to_return_a_string_with_full_name() {
    Name name = new Name("John", "Doe");
    assertThat(name.toString()).isEqualTo("John Doe");
  }
}
