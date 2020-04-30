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
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserGroupAggregatorTest {

  private static final String APPLICATION_ID = "e3968bda-b73c-4844-a89c-6a881631a3b5";
  private static final String USER_GROUP_NAME = "beta";

  @Mock
  private Application application;

  private UserGroupAggregator userGroupAggregator;

  @BeforeEach
  void setUp() {
    userGroupAggregator = new UserGroupAggregator();
  }

  @Test
  void constructor_should_set_application() {
    userGroupAggregator = new UserGroupAggregator(application);

    assertThat(userGroupAggregator)
        .hasFieldOrPropertyWithValue("application", application);
  }

  @Test
  void should_set_and_return_id() {
    userGroupAggregator.setId(APPLICATION_ID);

    assertThat(userGroupAggregator.getId())
        .isEqualTo(APPLICATION_ID);
  }

  @Test
  void should_set_and_return_application() {
    userGroupAggregator.setApplication(application);

    assertThat(userGroupAggregator.getApplication())
        .isEqualTo(application);
  }

  @Test
  void should_add_user_group() {
    userGroupAggregator.addUserGroup(USER_GROUP_NAME);

    assertThat(userGroupAggregator.getUserGroups())
        .containsKey(USER_GROUP_NAME);
  }

  @Test
  void should_throw_exception_trying_to_add_group_with_same_name() {
    userGroupAggregator.addUserGroup(USER_GROUP_NAME);

    assertThatThrownBy(() -> userGroupAggregator.addUserGroup(USER_GROUP_NAME))
        .isInstanceOf(ObjectAlreadyExistsInAggregate.class);
  }

  @Test
  void should_remove_user_group() {
    userGroupAggregator.addUserGroup(USER_GROUP_NAME);

    userGroupAggregator.removeUserGroup(USER_GROUP_NAME);

    assertThat(userGroupAggregator.containsUserGroup(USER_GROUP_NAME))
        .isFalse();
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
}
