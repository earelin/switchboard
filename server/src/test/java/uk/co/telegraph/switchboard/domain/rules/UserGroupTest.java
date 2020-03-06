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

package uk.co.telegraph.switchboard.domain.rules;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switchboard.domain.UserGroup;

class UserGroupTest {

  private static final String USER_GROUP_ID = "beta";

  private UserGroup userGroup;

  @BeforeEach
  void setUp() {
    userGroup = new UserGroup(USER_GROUP_ID);
  }

  @Test
  void should_set_user_id_on_constructor() {
    assertThat(userGroup)
        .hasFieldOrPropertyWithValue("id", USER_GROUP_ID);
  }

  @Test
  void should_get_user_id() {
    assertThat(userGroup.getId())
        .isEqualTo(USER_GROUP_ID);
  }

}
