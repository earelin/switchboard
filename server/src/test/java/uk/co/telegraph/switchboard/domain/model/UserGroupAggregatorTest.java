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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switchboard.domain.model.UserGroupAggregator;

class UserGroupAggregatorTest {

  private static final String APPLICATION_ID = "e3968bda-b73c-4844-a89c-6a881631a3b5";
  private static final String USER_GROUP_ID = "beta";
  private static final String USER_GROUP_NAME = "Beta";

  private UserGroupAggregator userGroupAggregator;

  @BeforeEach
  void setUp() {
    userGroupAggregator = new UserGroupAggregator();
  }

  @Test
  void should_add_user_group() {
    userGroupAggregator.addUserGroup(USER_GROUP_NAME);

    assertThat(userGroupAggregator.getUserGroups())
        .extracting("name")
        .contains(USER_GROUP_NAME);
  }

  @Test
  void should_remove_user_group() {
    userGroupAggregator.addUserGroup(USER_GROUP_NAME);

    userGroupAggregator.removeUserGroup(USER_GROUP_NAME);

    assertThat(userGroupAggregator.getUserGroups())
        .extracting("name")
        .doesNotContain(USER_GROUP_NAME);
  }

  @Test
  void should_return_true_if_user_group_exists() {
    userGroupAggregator.addUserGroup(USER_GROUP_NAME);

    assertThat(userGroupAggregator.containsUserGroup(USER_GROUP_NAME))
        .isTrue();
  }

  @Test
  void should_return_false_if_user_group_does_not_exists() {
    assertThat(userGroupAggregator.containsUserGroup(USER_GROUP_NAME))
        .isFalse();
  }

  @Test
  void should_return_string_representation() {
    assertThat(userGroupAggregator.toString())
        .startsWith("UserGroupAggregator");
  }
}
