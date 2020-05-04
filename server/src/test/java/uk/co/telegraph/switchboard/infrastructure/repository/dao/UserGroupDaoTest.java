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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserGroupDaoTest {
  public static final String USER_GROUP_NAME = "the-shire";
  public static final String[] USER_GROUP_USERS = {"frodo", "sam"};

  public static final String USER_GROUP_NAME_ALT = "human";
  public static final String[] USER_GROUP_USERS_ALT = {"aragorn", "boromir"};

  private UserGroupDao userGroupDao;

  @BeforeEach
  void setUp() {
    userGroupDao = new UserGroupDao();
    userGroupDao.setName(USER_GROUP_NAME);
    userGroupDao.setUsers(new HashSet<>(Arrays.asList(USER_GROUP_USERS)));
  }

  @Test
  void should_set_and_return_name() {
    userGroupDao.setName(USER_GROUP_NAME_ALT);

    assertThat(userGroupDao.getName())
        .isEqualTo(USER_GROUP_NAME_ALT);
  }

  @Test
  void should_set_and_return_users() {
    Set<String> users = new HashSet<>(Arrays.asList(USER_GROUP_USERS_ALT));
    userGroupDao.setUsers(users);

    assertThat(userGroupDao.getUsers())
        .isEqualTo(users);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(userGroupDao.equals(userGroupDao))
        .isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    UserGroupDao compareObject = new UserGroupDao();
    compareObject.setName(USER_GROUP_NAME);

    assertThat(userGroupDao.equals(compareObject))
        .isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(userGroupDao.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(userGroupDao.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_id() {
    UserGroupDao compareObject = new UserGroupDao();
    compareObject.setName(USER_GROUP_NAME_ALT);

    assertThat(userGroupDao.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    UserGroupDao compareObject = new UserGroupDao();
    compareObject.setName(USER_GROUP_NAME);

    assertThat(userGroupDao.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_ID() {
    UserGroupDao compareObject = new UserGroupDao();
    compareObject.setName(USER_GROUP_NAME_ALT);

    assertThat(userGroupDao.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_a_string_representation() throws IOException {
    assertThat(userGroupDao.toString())
        .contains(
            "UserGroupDao",
            USER_GROUP_NAME,
            USER_GROUP_USERS[0],
            USER_GROUP_USERS[1]);
  }
}
