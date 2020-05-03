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

package uk.co.telegraph.switchboard.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.google.common.collect.Sets;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserGroupTest {

  public static final String USER_GROUP_NAME = "beta";
  public static final Set<String> USER_GROUP_USERS
      = Sets.newHashSet("frodo", "sam", "gandalf");

  public static final String USER_GROUP_ALT_NAME = "qa";
  public static final Set<String> USER_GROUP_ALT_USERS
      = Sets.newHashSet("sauron", "saruman");

  public static final String USER_NAME = "aragorn";

  private UserGroup userGroup;

  @Mock
  private UserGroupAggregator userGroupAggregator;

  @BeforeEach
  void setUp() {
    userGroup = new UserGroup(USER_GROUP_NAME, USER_GROUP_USERS);
  }

  @Test
  void should_set_name_and_users_in_constructor() {
    assertThat(userGroup)
        .hasFieldOrPropertyWithValue("name", USER_GROUP_NAME);
    assertThat(userGroup.getUsers())
        .isEqualTo(USER_GROUP_USERS);
  }

  @Test
  void should_add_an_user_by_name() {
    userGroup.addUser(USER_NAME);

    assertThat(userGroup.getUsers())
        .contains(USER_NAME);
  }

  @Test
  void should_throw_validation_exception_when_add_null_user() {
    assertThatThrownBy(() -> userGroup.addUser(null))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
          "",
          "   ",
          "CxQdrPbLJFZDsdE55z482zC5k1Xlcv4Ju",
          "NyLJWskvH6TLR9bzLzscI6Ol6vr2Av2bOVKA3FXFdjQX2wEZsTzqn5pYvkg96nKS"
      })
  void should_throw_validation_exception_when_add_not_valid_user(String name) {
    assertThatThrownBy(() -> userGroup.addUser(name))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void should_remove_an_user_if_it_does_exists() {
    userGroup.removeUser("frodo");

    assertThat(userGroup.getUsers())
        .contains("sam", "gandalf");
  }

  @Test
  void should_return_true_if_an_user_exists() {
    assertThat(userGroup.containsUser("frodo"))
        .isTrue();
  }

  @Test
  void should_return_false_if_an_user_does_not_exists() {
    assertThat(userGroup.containsUser("sauron"))
        .isFalse();
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(userGroup.equals(userGroup))
        .isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    UserGroup compareObject = new UserGroup(USER_GROUP_NAME, USER_GROUP_ALT_USERS);

    assertThat(userGroup.equals(compareObject))
        .isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(userGroup.equals(null))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(userGroup.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_id() {
    UserGroup compareObject = new UserGroup(USER_GROUP_ALT_NAME, USER_GROUP_USERS);

    assertThat(userGroup.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_itself() {
    assertThat(userGroup.hashCode())
        .isEqualTo(userGroup.hashCode());
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    UserGroup compareObject = new UserGroup(USER_GROUP_NAME, USER_GROUP_ALT_USERS);

    assertThat(userGroup.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_have_a_different_hash_code_than_a_object_with_differnt_id() {
    UserGroup compareObject = new UserGroup(USER_GROUP_ALT_NAME, USER_GROUP_USERS);

    assertThat(userGroup.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_string_representation() {
    assertThat(userGroup.toString())
        .startsWith("UserGroup");
  }
}
