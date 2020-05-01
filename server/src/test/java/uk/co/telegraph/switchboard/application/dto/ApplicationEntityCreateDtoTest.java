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

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationEntityCreateDtoTest {

  private static final String APPLICATION_NAME = "Website";
  private static final String APPLICATION_DESCRIPTION = "Public website";

  private static final ValidatorFactory validatorFactory =
      Validation.buildDefaultValidatorFactory();

  private Validator validator;
  private ApplicationCreateDto applicationCreateDto;

  @BeforeEach
  void setUp() {
    validator = validatorFactory.getValidator();
    applicationCreateDto = new ApplicationCreateDto();
  }

  @Test
  void should_set_name_and_description_on_constructor() {
    applicationCreateDto = new ApplicationCreateDto(APPLICATION_NAME, APPLICATION_DESCRIPTION);

    assertThat(applicationCreateDto)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION);
  }

  @Test
  void should_set_and_return_name() {
    applicationCreateDto.setName(APPLICATION_NAME);

    assertThat(applicationCreateDto.getName()).isEqualTo(APPLICATION_NAME);
  }

  @Test
  void should_set_and_return_description() {
    applicationCreateDto.setDescription(APPLICATION_DESCRIPTION);

    assertThat(applicationCreateDto.getDescription()).isEqualTo(APPLICATION_DESCRIPTION);
  }

  @Test
  void should_validate() {
    applicationCreateDto.setName(APPLICATION_NAME);

    Set<ConstraintViolation<ApplicationCreateDto>> violations =
        validator.validate(applicationCreateDto);

    assertThat(violations).isEmpty();
  }

  @Test
  void should_not_validate_if_name_is_null() {
    Set<ConstraintViolation<ApplicationCreateDto>> violations =
        validator.validate(applicationCreateDto);

    assertThat(violations).hasSize(1);
    ConstraintViolation<ApplicationCreateDto> violation = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
  }

  @Test
  void should_not_validate_if_name_is_empty() {
    applicationCreateDto.setName("   ");

    Set<ConstraintViolation<ApplicationCreateDto>> violations =
        validator.validate(applicationCreateDto);

    assertThat(violations).hasSize(1);
    ConstraintViolation<ApplicationCreateDto> violation = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
  }

  @Test
  void should_return_string_representation() {
    assertThat(applicationCreateDto.toString()).startsWith("ApplicationCreateDto");
  }
}
