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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/** User groups aggregator. */
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class UserGroupAggregator {

  @Id
  private String id;

  @MapsId
  @OneToOne
  @EqualsAndHashCode.Include
  private Application application;

  @OneToMany(mappedBy = "userGroupAggregator")
  @MapKey(name = "name")
  private Map<String, UserGroup> userGroups = new HashMap<>();

  UserGroupAggregator() {}

  public UserGroupAggregator(Application application) {
    this.application = application;
  }

  public void addUserGroup(String name) {
    if (!containsUserGroup(name)) {
      UserGroup userGroup = new UserGroup(this, name);
      this.userGroups.put(name, userGroup);
    } else {
      String message = String.format("Object with name %s already exists in aggregate", name);
      throw new ObjectAlreadyExistsInAggregate(message);
    }
  }

  public boolean containsUserGroup(String name) {
    return this.userGroups.containsKey(name);
  }

  public void removeUserGroup(String name) {
    this.userGroups.remove(name);
  }

  public String getId() {
    return this.id;
  }

  void setId(String id) {
    this.id = id;
  }

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public Map<String, UserGroup> getUserGroups() {
    return Map.copyOf(userGroups);
  }

  void setUserGroups(
      Map<String, UserGroup> userGroups) {
    this.userGroups = new HashMap<>(userGroups);
  }
}
