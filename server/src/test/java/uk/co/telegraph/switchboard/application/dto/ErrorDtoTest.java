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

package uk.co.telegraph.switchboard.application.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switchboard.domain.validation.ValidationException;

class ErrorDtoTest {
  public static final int ERROR_CODE = 400;
  public static final String ERROR_MESSAGE = "Could not open the file";
  public static final String ERROR_MESSAGE_ALT = "Connection error";

  private ErrorDto errorDto;

  @BeforeEach
  void setUp() {
    errorDto = new ErrorDto();
  }

  @Test
  void should_set_and_return_code() {
    errorDto.setCode(ERROR_CODE);

    assertThat(errorDto.getCode())
        .isEqualTo(ERROR_CODE);
  }

  @Test
  void should_set_and_return_message() {
    errorDto.setMessage(ERROR_MESSAGE);

    assertThat(errorDto.getMessage())
        .isEqualTo(ERROR_MESSAGE);
  }

  @Test
  void should_build_error_from_validation_exception() {
    ValidationException validationException = new ValidationException(ERROR_MESSAGE_ALT);
    ErrorDto errorDto = ErrorDto.fromValidationException(validationException);

    assertThat(errorDto)
        .hasFieldOrPropertyWithValue("message", ERROR_MESSAGE_ALT)
        .hasFieldOrPropertyWithValue("code", 400);
  }

  @Test
  void should_return_string_representation() {
    Assertions.assertThat(errorDto.toString())
        .startsWith("ErrorDto");
  }
}
