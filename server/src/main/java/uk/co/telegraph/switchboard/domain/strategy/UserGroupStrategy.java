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
 *
 */

package uk.co.telegraph.switchboard.domain.strategy;

import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import uk.co.telegraph.switchboard.domain.ClientInfo;
import uk.co.telegraph.switchboard.domain.UserGroup;

@Entity
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(
    callSuper = true,
    onlyExplicitlyIncluded = true
)
public class UserGroupStrategy extends Strategy {

  @ManyToMany
  private Set<UserGroup> userGroups;

  @Override
  public boolean isFeatureEnabledForClient(ClientInfo clientInfo) {
    if (Objects.isNull(userGroups)) {
      return false;
    }

    return userGroups.stream()
        .map(userGroup -> userGroup.hasUser(clientInfo.getUser()))
        .reduce(Boolean::logicalOr)
        .orElse(false);
  }
}
