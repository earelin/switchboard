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

package uk.co.telegraph.switchboard.infrastructure.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationDaoTest {

  public static final String APPLICATION_ID = "1dc58fb4-d63f-47c7-882e-8ac02daf2fd5";
  public static final String APPLICATION_NAME = "Website";
  public static final String APPLICATION_DESCRIPTION = "Public website";
  public static final String APPLICATION_SECRET = "e4kMgtLTJ8VFJpkX";

  public static final String APPLICATION_ID_ALT = "e612252f-51f7-4ad3-84b7-dca29a33571e";
  public static final String APPLICATION_NAME_ALT = "Mobile";
  public static final String APPLICATION_DESCRIPTION_ALT = "Mobile application";
  public static final String APPLICATION_SECRET_ALT = "rZa5rV6g3sJpyE8j";

  private ApplicationDao applicationDao;

  @BeforeEach
  void setUp() throws IOException {
    applicationDao = new ApplicationDao();
    applicationDao.setId(APPLICATION_ID);
    applicationDao.setName(APPLICATION_NAME);
    applicationDao.setDescription(APPLICATION_DESCRIPTION);
    applicationDao.setSecret(APPLICATION_SECRET);
  }

  @Test
  void should_set_and_return_id() {
    applicationDao.setId(APPLICATION_ID_ALT);

    assertThat(applicationDao.getId())
        .isEqualTo(APPLICATION_ID_ALT);
  }

  @Test
  void should_set_and_return_name() {
    applicationDao.setName(APPLICATION_NAME_ALT);

    assertThat(applicationDao.getName())
        .isEqualTo(APPLICATION_NAME_ALT);
  }

  @Test
  void should_set_and_return_description() {
    applicationDao.setDescription(APPLICATION_DESCRIPTION_ALT);

    assertThat(applicationDao.getDescription())
        .isEqualTo(APPLICATION_DESCRIPTION_ALT);
  }

  @Test
  void should_set_and_return_secret() {
    applicationDao.setSecret(APPLICATION_SECRET_ALT);

    assertThat(applicationDao.getSecret())
        .isEqualTo(APPLICATION_SECRET_ALT);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(applicationDao.equals(applicationDao))
        .isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    ApplicationDao compareObject = new ApplicationDao();
    compareObject.setId(APPLICATION_ID);

    assertThat(applicationDao.equals(compareObject))
        .isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(applicationDao.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(applicationDao.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_id() {
    ApplicationDao compareObject = new ApplicationDao();
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(applicationDao.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    ApplicationDao compareObject = new ApplicationDao();
    compareObject.setId(APPLICATION_ID);

    assertThat(applicationDao.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_ID() {
    ApplicationDao compareObject = new ApplicationDao();
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(applicationDao.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_a_string_representation() throws IOException {
    assertThat(applicationDao.toString())
        .contains(
            "ApplicationDao",
            APPLICATION_ID,
            APPLICATION_NAME,
            APPLICATION_DESCRIPTION,
            APPLICATION_SECRET);
  }
}
