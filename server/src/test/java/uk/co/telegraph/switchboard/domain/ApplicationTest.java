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

package uk.co.telegraph.switchboard.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switchboard.domain.validation.ValidationException;

class ApplicationTest {

  public static final String APPLICATION_ID = "5cfd7920-1f9f-4fb3-a975-0393fccaa068";
  public static final String APPLICATION_ID_ALT = "b6acb171-65f6-4000-837c-0cf1184a918e";
  public static final String APPLICATION_NAME = "Authoring";
  public static final String APPLICATION_NAME_ALT = "CMS";
  public static final String APPLICATION_DESCRIPTION = "A great authoring application";
  public static final String APPLICATION_SECRET = "XQqhNWDvEmnK777R";
  public static final String APPLICATION_SECRET_ALT = "qR7yQdMEEZn7XsCU";

  private Application application;

  @BeforeEach
  void setUp() {
    application = new Application(APPLICATION_ID, APPLICATION_NAME, APPLICATION_SECRET);
  }

  @Test
  void constructor_should_set_id_name_and_secret() {
    assertThat(application)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }

  @Test
  void should_not_allow_to_construct_with_a_blank_id() {
    assertThatThrownBy(() -> new Application("  ", APPLICATION_NAME, APPLICATION_SECRET))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_construct_with_a_null_id() {
    assertThatThrownBy(() -> new Application(null, APPLICATION_NAME, APPLICATION_SECRET))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_construct_with_a_id_longer_that_36_chars() {
    assertThatThrownBy(
        () -> new Application(
                    "000-125c12a7-bf9a-4795-b673-564f9d9e369c",
                    APPLICATION_NAME,
                    APPLICATION_SECRET))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_construct_with_a_blank_name() {
    assertThatThrownBy(() -> new Application(APPLICATION_ID, "   ", APPLICATION_SECRET))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_construct_with_a_null_name() {
    assertThatThrownBy(() -> new Application(APPLICATION_ID, null, APPLICATION_SECRET))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_construct_with_a_name_longer_than_64_chars() {
    assertThatThrownBy(() ->
        new Application(
            APPLICATION_ID,
            "An amazing application that is improving our productivity a lot every day",
            APPLICATION_SECRET))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_construct_with_a_blank_secret() {
    assertThatThrownBy(() -> new Application(APPLICATION_ID, APPLICATION_NAME, "   "))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_construct_with_a_null_secret() {
    assertThatThrownBy(() -> new Application(APPLICATION_ID, APPLICATION_NAME, null))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_construct_with_a_secret_longer_than_16_chars() {
    assertThatThrownBy(
        () -> new Application(APPLICATION_ID, APPLICATION_NAME, "qR7yQdMEEZn7XsCUasD4g5"))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_set_and_return_id() {
    application.setId(APPLICATION_ID_ALT);

    assertThat(application.getId()).isEqualTo(APPLICATION_ID_ALT);
  }

  @Test
  void should_not_allow_to_set_a_null_id() {
    assertThatThrownBy(() -> application.setId(null))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_set_a_blank_id() {
    assertThatThrownBy(() -> application.setId("   "))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_set_an_id_longer_than_16_chars() {
    assertThatThrownBy(() -> application.setId("000-125c12a7-bf9a-4795-b673-564f9d9e369c"))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_set_and_return_name() {
    application.setName(APPLICATION_NAME_ALT);

    assertThat(application.getName()).isEqualTo(APPLICATION_NAME_ALT);
  }

  @Test
  void should_not_allow_to_set_a_null_name() {
    assertThatThrownBy(() -> application.setName(null))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_set_a_blank_name() {
    assertThatThrownBy(() -> application.setName("  "))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_set_a_name_longer_than_64_chars() {
    assertThatThrownBy(
        () -> application.setName(
            "An amazing application that is improving our productivity a lot every day"))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_set_and_return_description() {
    application.setDescription(APPLICATION_DESCRIPTION);

    assertThat(application.getDescription()).isEqualTo(APPLICATION_DESCRIPTION);
  }

  @Test
  void should_set_and_return_secret() {
    application.setSecret(APPLICATION_SECRET_ALT);

    assertThat(application.getSecret()).isEqualTo(APPLICATION_SECRET_ALT);
  }

  @Test
  void should_not_allow_to_set_a_null_secret() {
    assertThatThrownBy(() -> application.setSecret(null))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_set_a_blank_secret() {
    assertThatThrownBy(() -> application.setSecret("  "))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_not_allow_to_set_a_secret_longer_than_12_chars() {
    assertThatThrownBy(() -> application.setSecret("qR7yQdMEEZn7XsCUasD4g5"))
        .isInstanceOf(ValidationException.class);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(application.equals(application)).isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    Application compareObject = new Application(APPLICATION_ID, APPLICATION_NAME_ALT, APPLICATION_SECRET_ALT);
    compareObject.setId(APPLICATION_ID);

    assertThat(application.equals(compareObject)).isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(application.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(application.equals(compareObject)).isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_id() {
    Application compareObject = new Application(APPLICATION_ID_ALT, APPLICATION_NAME, APPLICATION_SECRET);
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(application.equals(compareObject)).isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    Application compareObject = new Application(APPLICATION_ID, APPLICATION_NAME_ALT, APPLICATION_SECRET_ALT);
    compareObject.setId(APPLICATION_ID);

    assertThat(application.hashCode()).isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_ID() {
    Application compareObject = new Application(APPLICATION_ID_ALT, APPLICATION_NAME, APPLICATION_SECRET);
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(application.hashCode()).isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_string_representation() {
    assertThat(application.toString()).startsWith("Application");
  }
}
