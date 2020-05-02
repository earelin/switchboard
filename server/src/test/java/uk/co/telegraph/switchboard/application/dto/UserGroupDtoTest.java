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

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserGroupDtoTest {

  public static final String USER_GROUP_NAME = "beta";
  public static final Set<String> USER_GROUP_USERS
      = Sets.newHashSet("frodo", "sam", "gandalf");

  public static final String USER_GROUP_NAME_ALT = "qa";
  public static final Set<String> USER_GROUP_USERS_ALT
      = Sets.newHashSet("sauron", "saruman");

  private UserGroupDto userGroupDto;

  @BeforeEach
  void setUp() {
    userGroupDto = new UserGroupDto();
    userGroupDto.setName(USER_GROUP_NAME);
    userGroupDto.setUsers(USER_GROUP_USERS);
  }

  @Test
  void should_set_and_return_name() {
    userGroupDto.setName(USER_GROUP_NAME_ALT);

    assertThat(userGroupDto.getName())
        .isEqualTo(USER_GROUP_NAME_ALT);
  }

  @Test
  void should_set_and_return_users() {
    userGroupDto.setUsers(USER_GROUP_USERS_ALT);

    assertThat(userGroupDto.getUsers())
        .isEqualTo(USER_GROUP_USERS_ALT);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(userGroupDto.equals(userGroupDto))
        .isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    UserGroupDto compareObject = new UserGroupDto();
    compareObject.setName(USER_GROUP_NAME);

    assertThat(userGroupDto.equals(compareObject))
        .isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(userGroupDto.equals(null))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(userGroupDto.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_id() {
    UserGroupDto compareObject = new UserGroupDto();
    compareObject.setName(USER_GROUP_NAME_ALT);

    assertThat(userGroupDto.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    UserGroupDto compareObject = new UserGroupDto();
    compareObject.setName(USER_GROUP_NAME);

    assertThat(userGroupDto.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_ID() {
    UserGroupDto compareObject = new UserGroupDto();
    compareObject.setName(USER_GROUP_NAME_ALT);

    assertThat(userGroupDto.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_string_representation() {
    assertThat(userGroupDto.toString())
        .startsWith("UserGroupDto");
  }
}
