/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.telegraph.switchboard.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switchboard.domain.ClientInfo;
import uk.co.telegraph.switchboard.domain.UserGroup;

class UserGroupStrategyTest {

  private static final Long ID = 25L;
  private static final Set<UserGroup> USER_GROUPS = generateUserGroup();

  private UserGroupStrategy userGroupStrategy;

  @BeforeEach
  void setUp() {
    userGroupStrategy = new UserGroupStrategy();
    userGroupStrategy.setId(ID);
    userGroupStrategy.setUserGroups(USER_GROUPS);
  }

  @Test
  void shouldSetIdOnConstructor() {
    assertThat(userGroupStrategy.getId())
        .isEqualTo(ID);
  }

  @Test
  void shouldSetUsers() {
    assertThat(userGroupStrategy.getUserGroups())
        .isEqualTo(USER_GROUPS);
  }

  @Test
  void shouldReturnIsEnabledFalseIfUserGroupIsNotSet() {
    userGroupStrategy.setUserGroups(null);
    ClientInfo clientInfo = ClientInfo.builder()
        .application("newsroom-dashboard")
        .context("production")
        .instance("pc-3456")
        .dateTime(ZonedDateTime.now())
        .user("gandalf")
        .build();

    assertThat(userGroupStrategy.isFeatureEnabledForClient(clientInfo))
        .isFalse();
  }

  @Test
  void shouldReturnIsEnabledTrueIfUserGroupContainsClientInfoUser() {
    ClientInfo clientInfo = ClientInfo.builder()
        .application("newsroom-dashboard")
        .context("production")
        .instance("pc-3456")
        .dateTime(ZonedDateTime.now())
        .user("gandalf")
        .build();

    assertThat(userGroupStrategy.isFeatureEnabledForClient(clientInfo))
        .isTrue();
  }

  @Test
  void shouldReturnIsEnabledFalseIfUserGroupNotContainsClientInfoUser() {
    ClientInfo clientInfo = ClientInfo.builder()
        .application("newsroom-dashboard")
        .context("production")
        .instance("pc-3456")
        .dateTime(ZonedDateTime.now())
        .user("sauron")
        .build();

    assertThat(userGroupStrategy.isFeatureEnabledForClient(clientInfo))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    UserGroupStrategy comparedObject = new UserGroupStrategy();
    comparedObject.setId(12L);

    assertThat(userGroupStrategy.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "string";

    assertThat(userGroupStrategy.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(userGroupStrategy)
        .isEqualTo(userGroupStrategy);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(userGroupStrategy)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToAUserGroupStrategyWithSameId() {
    UserGroupStrategy compareObject = new UserGroupStrategy();
    compareObject.setId(ID);

    assertThat(userGroupStrategy)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADefaultStrategyWithADifferentId() {
    UserGroupStrategy compareObject = new UserGroupStrategy();
    compareObject.setId(12L);
    compareObject.setUserGroups(USER_GROUPS);

    assertThat(userGroupStrategy)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(userGroupStrategy)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameInstanceShouldHaveSameHashCode() {
    assertThat(userGroupStrategy.hashCode())
        .isEqualTo(userGroupStrategy.hashCode());
  }

  @Test
  void twoUserGroupStrategiesWithTheSameIdShouldHaveSameHashCode() {
    UserGroupStrategy compareObject = new UserGroupStrategy();
    compareObject.setId(ID);

    assertThat(userGroupStrategy.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    UserGroupStrategy compareObject = new UserGroupStrategy();
    compareObject.setId(12L);
    compareObject.setUserGroups(generateUserGroup());

    assertThat(userGroupStrategy.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(userGroupStrategy.toString())
        .startsWith("UserGroupStrategy");
  }

  private static Set<UserGroup> generateUserGroup() {
    UserGroup userGroup1 = new UserGroup();
    userGroup1.setId(2L);
    userGroup1.setName("Beta users");
    userGroup1.setUsers(Set.of("frodo", "sam", "gandalf"));

    UserGroup userGroup2 = new UserGroup();
    userGroup2.setId(3L);
    userGroup2.setName("Testers");
    userGroup2.setUsers(Set.of("aragorn", "gandalf"));

    return Set.of(userGroup1, userGroup2);
  }
}
