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

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserGroupTest {

  private static final String KEY = "testers";
  private static final String NAME = "Testers";
  private static final Set<String> USERS = Set.of("sam", "frodo", "gandalf");

  private UserGroup userGroup;

  @BeforeEach
  void setUp() {
    userGroup = new UserGroup(KEY);
    userGroup.setName(NAME);
    userGroup.setUsers(USERS);
  }

  @Test
  void shouldSetKeyInConstruction() {
    assertThat(userGroup.getKey())
        .isEqualTo(KEY);
  }

  @Test
  void shouldSetName() {
    assertThat(userGroup.getName())
        .isEqualTo(NAME);
  }

  @Test
  void shouldSetUsers() {
    assertThat(userGroup.getUsers())
        .isEqualTo(USERS);
  }

  @Test
  void hasUserShouldReturnTrueIfAnUserIsInTheGroup() {
    assertThat(userGroup.hasUser("gandalf"))
        .isTrue();
  }

  @Test
  void hasUserShouldReturnFalseIfAnUserIsNotInTheGroup() {
    assertThat(userGroup.hasUser("sauron"))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    UserGroup comparedObject = new UserGroup("other-group");

    assertThat(userGroup.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "string";

    assertThat(userGroup.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(userGroup)
        .isEqualTo(userGroup);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(userGroup)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToADefaultStrategyWithSameId() {
    UserGroup compareObject = new UserGroup(KEY);

    assertThat(userGroup)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADefaultStrategyWithADifferentId() {
    UserGroup compareObject = new UserGroup("other-group");
    compareObject.setName(NAME);
    compareObject.setUsers(USERS);

    assertThat(userGroup)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(userGroup)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameInstanceShouldHaveSameHashCode() {
    assertThat(userGroup.hashCode())
        .isEqualTo(userGroup.hashCode());
  }

  @Test
  void twoApplicationsWithTheSameIdShouldHaveSameHashCode() {
    UserGroup compareObject = new UserGroup(KEY);

    assertThat(userGroup.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    UserGroup compareObject = new UserGroup("other-group");
    compareObject.setName(NAME);
    compareObject.setUsers(USERS);

    assertThat(userGroup.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(userGroup.toString())
        .isEqualTo("UserGroup("
            + "key=" + KEY + ", "
            + "name=" + NAME + ", "
            + "users=" + USERS.toString()
            + ")");
  }

}
