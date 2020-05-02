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

import static uk.co.telegraph.switchboard.domain.ClientInfo.USER_PROPERTY_KEY;

import java.util.Optional;
import uk.co.telegraph.switchboard.domain.ClientInfo;
import uk.co.telegraph.switchboard.domain.UserGroup;

public class UserGroupRule implements Rule {

  private final UserGroup userGroup;

  public UserGroupRule(UserGroup userGroup) {
    this.userGroup = userGroup;
  }

  @Override
  public boolean isEnabledForClient(ClientInfo clientInfo) {
    Optional<String> user = clientInfo.getPropertyValue(USER_PROPERTY_KEY);

    return user.isPresent() && userGroup.containsUser(user.get());
  }
}
