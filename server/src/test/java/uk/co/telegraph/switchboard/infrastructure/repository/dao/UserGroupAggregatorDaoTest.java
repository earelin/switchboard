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

import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.model.UserGroup;

@ExtendWith(MockitoExtension.class)
public class UserGroupAggregatorDaoTest {

  public static final String APPLICATION_ID = "1dc58fb4-d63f-47c7-882e-8ac02daf2fd5";
  public static final String APPLICATION_ID_ALT = "e612252f-51f7-4ad3-84b7-dca29a33571e";

  @Mock
  private UserGroup firstUserGroup;

  @Mock
  private UserGroup secondUserGroup;

  private UserGroupAggregatorDao userGroupAggregatorDao;

  @BeforeEach
  void setUp() {
    userGroupAggregatorDao = new UserGroupAggregatorDao();
    userGroupAggregatorDao.setId(APPLICATION_ID);
    userGroupAggregatorDao.setUserGroups(Sets.newHashSet(firstUserGroup, secondUserGroup));
  }

  @Test
  void should_set_and_return_id() {
    userGroupAggregatorDao.setId(APPLICATION_ID_ALT);

    assertThat(userGroupAggregatorDao.getId())
        .isEqualTo(APPLICATION_ID_ALT);
  }

  @Test
  void should_set_and_return_user_groups() {
    Set<UserGroup> userGroups = Sets.newHashSet(firstUserGroup);
    userGroupAggregatorDao.setUserGroups(userGroups);

    assertThat(userGroupAggregatorDao.getUserGroups())
        .isEqualTo(userGroups);
  }

  @Test
  void should_be_equal_to_itself() {
    assertThat(userGroupAggregatorDao.equals(userGroupAggregatorDao))
        .isTrue();
  }

  @Test
  void should_be_equal_to_an_object_with_same_id() {
    UserGroupAggregatorDao compareObject = new UserGroupAggregatorDao();
    compareObject.setId(APPLICATION_ID);

    assertThat(userGroupAggregatorDao.equals(compareObject))
        .isTrue();
  }

  @Test
  void should_not_be_equal_to_null() {
    assertThat(userGroupAggregatorDao.equals(null)).isFalse();
  }

  @Test
  void should_not_be_equal_to_a_different_class() {
    String compareObject = "2wertgyhuji";

    assertThat(userGroupAggregatorDao.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_not_be_equal_to_an_object_with_different_id() {
    UserGroupAggregatorDao compareObject = new UserGroupAggregatorDao();
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(userGroupAggregatorDao.equals(compareObject))
        .isFalse();
  }

  @Test
  void should_have_the_same_hash_code_than_a_object_with_same_id() {
    UserGroupAggregatorDao compareObject = new UserGroupAggregatorDao();
    compareObject.setId(APPLICATION_ID);

    assertThat(userGroupAggregatorDao.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void should_not_have_the_same_hash_code_than_an_object_with_different_id() {
    UserGroupAggregatorDao compareObject = new UserGroupAggregatorDao();
    compareObject.setId(APPLICATION_ID_ALT);

    assertThat(userGroupAggregatorDao.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void should_return_a_string_representation() throws IOException {
    assertThat(userGroupAggregatorDao.toString())
        .contains(
            "UserGroupAggregatorDao",
            APPLICATION_ID);
  }
}
