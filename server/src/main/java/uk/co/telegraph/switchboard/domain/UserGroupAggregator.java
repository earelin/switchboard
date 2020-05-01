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

import java.util.HashSet;
import java.util.Set;

/** User groups aggregator. */
public class UserGroupAggregator {

  private Application application;
  private Set<UserGroup> userGroups = new HashSet<>();

  UserGroupAggregator() {}

  public UserGroupAggregator(Application application) {
    this.application = application;
  }

  public void addUserGroup(String name) {
    this.userGroups.add(new UserGroup("id", name));
  }

  public boolean containsUserGroup(UserGroup userGroup) {
    return this.userGroups.contains(userGroup);
  }

  public void removeUserGroup(UserGroup userGroup) {
    this.userGroups.remove(userGroup);
  }

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public Set<UserGroup> getUserGroups() {
    return Set.copyOf(userGroups);
  }

  void setUserGroups(
      Set<UserGroup> userGroups) {
    this.userGroups = new HashSet<>(userGroups);
  }
}
