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
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.Context;

class UserGroupTest {

  private static final Long ID = 25L;
  private static final String NAME = "Testers";
  private static final Set<String> USERS = Set.of("sam", "frodo", "gandalf");
  private static final Application APPLICATION = generateApplication();

  private UserGroup userGroup;

  @BeforeEach
  void setUp() {
    userGroup = new UserGroup();
    userGroup.setId(ID);
    userGroup.setName(NAME);
    userGroup.setUsers(USERS);
    userGroup.setApplication(APPLICATION);
  }

  @Test
  void shouldSetId() {
    assertThat(userGroup.getId())
        .isEqualTo(ID);
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
  void shouldSetApplication() {
    assertThat(userGroup.getApplication())
        .isEqualTo(APPLICATION);
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
    UserGroup comparedObject = new UserGroup();
    comparedObject.setId(12L);

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
  void shouldNotCanEqualToNull() {
    assertThat(userGroup.canEqual(null))
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
  void shouldBeEqualToAnUserGroupWithSameId() {
    UserGroup compareObject = new UserGroup();
    compareObject.setId(ID);

    assertThat(userGroup)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAnUserGroupWithADifferentId() {
    UserGroup compareObject = new UserGroup();
    compareObject.setId(12L);
    compareObject.setName(NAME);
    compareObject.setUsers(USERS);
    userGroup.setApplication(APPLICATION);

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
    UserGroup compareObject = new UserGroup();
    compareObject.setId(ID);

    assertThat(userGroup.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    UserGroup compareObject = new UserGroup();
    compareObject.setId(12L);
    compareObject.setName(NAME);
    compareObject.setUsers(USERS);
    userGroup.setApplication(APPLICATION);

    assertThat(userGroup.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(userGroup.toString())
        .startsWith("UserGroup");
  }

  private static Application generateApplication() {
    Application application = new Application();
    application.setId("c0a8e1e5-e307-3c5b-b381-9b387b5f01fd");
    application.setName("One Application");
    application.setDescription("Lorem ipsum");
    application.setSecret("qwerty");
    application.setContexts(Set.of(Context.buildDefault()));

    return application;
  }

}
