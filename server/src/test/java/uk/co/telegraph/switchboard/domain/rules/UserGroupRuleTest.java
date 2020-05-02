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
import static org.mockito.Mockito.when;
import static uk.co.telegraph.switchboard.domain.ClientInfo.USER_PROPERTY_KEY;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.ClientInfo;
import uk.co.telegraph.switchboard.domain.UserGroup;

@ExtendWith(MockitoExtension.class)
class UserGroupRuleTest {

  @Mock
  private UserGroup userGroup;

  @Mock
  private ClientInfo clientInfo;

  @InjectMocks
  private UserGroupRule userGroupRole;

  @Test
  void constructor_should_set_user_group() {
    assertThat(userGroupRole)
        .hasFieldOrPropertyWithValue("userGroup", userGroup);
  }

  @Test
  void should_report_enabled_if_user_is_in_group() {
    when(clientInfo.getPropertyValue(USER_PROPERTY_KEY))
        .thenReturn(Optional.of("john"));
    when(userGroup.containsUser("john"))
        .thenReturn(true);

    assertThat(userGroupRole.isEnabledForClient(clientInfo))
        .isTrue();
  }

  @Test
  void should_report_disabled_if_user_is_null() {
    when(clientInfo.getPropertyValue(USER_PROPERTY_KEY))
        .thenReturn(Optional.empty());

    assertThat(userGroupRole.isEnabledForClient(clientInfo))
        .isFalse();
  }

  @Test
  void should_report_disabled_if_user_is_not_in_group() {
    when(clientInfo.getPropertyValue(USER_PROPERTY_KEY))
        .thenReturn(Optional.of("john"));
    when(userGroup.containsUser("john"))
        .thenReturn(false);

    assertThat(userGroupRole.isEnabledForClient(clientInfo))
        .isFalse();
  }
}
