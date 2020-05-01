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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationEntityDtoTest {

  private static final String APPLICATION_ID = "ac3747c3-9b49-414c-9d3a-7a38383a49d7";
  private static final String APPLICATION_NAME = "Website";
  private static final String APPLICATION_DESCRIPTION = "Public website";
  private static final String APPLICATION_SECRET = "5keMeAAF69sBQqau";

  private ApplicationDto applicationDto;

  @BeforeEach
  void setUp() {
    applicationDto = new ApplicationDto();
  }

  @Test
  void should_set_and_return_id() {
    applicationDto.setId(APPLICATION_ID);

    assertThat(applicationDto.getId()).isEqualTo(APPLICATION_ID);
  }

  @Test
  void should_set_and_return_name() {
    applicationDto.setName(APPLICATION_NAME);

    assertThat(applicationDto.getName()).isEqualTo(APPLICATION_NAME);
  }

  @Test
  void should_set_and_return_description() {
    applicationDto.setDescription(APPLICATION_DESCRIPTION);

    assertThat(applicationDto.getDescription()).isEqualTo(APPLICATION_DESCRIPTION);
  }

  @Test
  void should_set_and_return_secret() {
    applicationDto.setSecret(APPLICATION_SECRET);

    assertThat(applicationDto.getSecret()).isEqualTo(APPLICATION_SECRET);
  }

  @Test
  void should_return_string_representation() {
    assertThat(applicationDto.toString()).startsWith("ApplicationDto");
  }
}
