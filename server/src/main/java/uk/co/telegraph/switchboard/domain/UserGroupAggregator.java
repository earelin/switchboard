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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.ToString;

/** User groups aggregator. */
@ToString
public class UserGroupAggregator {

  private final Map<String, UserGroup> userGroups = new HashMap<>();

  public UserGroupAggregator() {}

  public UserGroupAggregator(Set<UserGroup> userGroups) {
    if (userGroups != null) {
      userGroups.forEach(userGroup -> this.userGroups.put(userGroup.getName(), userGroup));
    }
  }

  public void addUserGroup(String name) {
    this.userGroups.put(name, new UserGroup(name));
  }

  public boolean containsUserGroup(String name) {
    return this.userGroups.containsKey(name);
  }

  public void removeUserGroup(String name) {
    this.userGroups.remove(name);
  }

  public Set<UserGroup> getUserGroups() {
    return Set.copyOf(userGroups.values());
  }
}
