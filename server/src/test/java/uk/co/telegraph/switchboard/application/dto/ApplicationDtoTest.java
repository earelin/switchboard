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

class ApplicationDtoTest {

  private static final String APPLICATION_ID = "ac3747c3-9b49-414c-9d3a-7a38383a49d7";
  private static final String APPLICATION_NAME = "Website";
  private static final String APPLICATION_DESCRIPTION = "Public website";
  private static final String APPLICATION_SECRET = "5keMeAAF69sBQqau";

  private static final String APPLICATION_ID_ALT = "5236390e-4861-47f5-8cde-2e4b196e3e58";
  private static final String APPLICATION_NAME_ALT = "Mobile";
  private static final String APPLICATION_DESCRIPTION_ALT = "Mobile application";
  private static final String APPLICATION_SECRET_ALT = "DILZbyO6a5ApRGQY";

  private ApplicationDto applicationDto;

  @BeforeEach
  void setUp() {
    applicationDto = new ApplicationDto();
    applicationDto.setId(APPLICATION_ID);
    applicationDto.setName(APPLICATION_NAME);
    applicationDto.setDescription(APPLICATION_DESCRIPTION);
    applicationDto.setSecret(APPLICATION_SECRET);
  }

  @Test
  void should_set_and_return_id() {
    applicationDto.setId(APPLICATION_ID_ALT);

    assertThat(applicationDto.getId())
        .isEqualTo(APPLICATION_ID_ALT);
  }

  @Test
  void should_set_and_return_name() {
    applicationDto.setName(APPLICATION_NAME_ALT);

    assertThat(applicationDto.getName())
        .isEqualTo(APPLICATION_NAME_ALT);
  }

  @Test
  void should_set_and_return_description() {
    applicationDto.setDescription(APPLICATION_DESCRIPTION_ALT);

    assertThat(applicationDto.getDescription())
        .isEqualTo(APPLICATION_DESCRIPTION_ALT);
  }

  @Test
  void should_set_and_return_secret() {
    applicationDto.setSecret(APPLICATION_SECRET_ALT);

    assertThat(applicationDto.getSecret())
        .isEqualTo(APPLICATION_SECRET_ALT);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(applicationDto.equals(applicationDto))
        .isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    ApplicationDto compareObject = new ApplicationDto();
    compareObject.setId(APPLICATION_ID);

    assertThat(applicationDto.equals(compareObject))
        .isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(applicationDto.equals(null))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(applicationDto.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_id() {
    ApplicationDto compareObject = new ApplicationDto();
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(applicationDto.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    ApplicationDto compareObject = new ApplicationDto();
    compareObject.setId(APPLICATION_ID);

    assertThat(applicationDto.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_ID() {
    ApplicationDto compareObject = new ApplicationDto();
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(applicationDto.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_string_representation() {
    assertThat(applicationDto.toString())
        .startsWith("ApplicationDto");
  }
}
