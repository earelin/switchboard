/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.telegraph.switchboard.domain.validation;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class StringFieldValidatorTest {

  @Test
  void should_validate_field_be_null_as_error() {
    StringFieldValidator stringFieldValidator = StringFieldValidator.builder()
        .fieldName("name")
        .shouldNotBeNull()
        .build();

    assertThatThrownBy(() -> stringFieldValidator.apply(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   ", "One Name"})
  void should_validate_field_not_be_null(String name) {
    StringFieldValidator stringFieldValidator = StringFieldValidator.builder()
        .fieldName("name")
        .shouldNotBeNull()
        .build();

    assertThatCode(() -> stringFieldValidator.apply(name))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @ValueSource(strings = {"", "   "})
  void should_validate_field_be_blank_as_error(String name) {
    StringFieldValidator stringFieldValidator = StringFieldValidator.builder()
        .fieldName("name")
        .shouldNotBeBlank()
        .build();

    assertThatThrownBy(() -> stringFieldValidator.apply(name))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void should_validate_field_null_as_not_blank_error() {
    StringFieldValidator stringFieldValidator = StringFieldValidator.builder()
        .fieldName("name")
        .shouldNotBeBlank()
        .build();

    assertThatCode(() -> stringFieldValidator.apply(null))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @ValueSource(strings = {"One name", "Other name   "})
  void should_validate_field_not_be_blank(String name) {
    StringFieldValidator stringFieldValidator = StringFieldValidator.builder()
        .fieldName("name")
        .shouldNotBeBlank()
        .build();

    assertThatCode(() -> stringFieldValidator.apply(name))
        .doesNotThrowAnyException();
  }


  @ParameterizedTest
  @ValueSource(strings = {"One name", "Label"})
  void should_validate_field_not_be_longer_than_8_chars(String name) {
    StringFieldValidator stringFieldValidator = StringFieldValidator.builder()
        .fieldName("name")
        .shouldNotBeLongerThan(8)
        .build();

    assertThatCode(() -> stringFieldValidator.apply(name))
        .doesNotThrowAnyException();
  }

  @ParameterizedTest
  @ValueSource(strings = {"One name ", "Other name   "})
  void should_validate_field_be_longer_than_8_chars(String name) {
    StringFieldValidator stringFieldValidator = StringFieldValidator.builder()
        .fieldName("name")
        .shouldNotBeLongerThan(8)
        .build();

    assertThatThrownBy(() -> stringFieldValidator.apply(name))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
