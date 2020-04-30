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
  @MapKey(name = "id")
  private Map<Long, UserGroup> userGroups = new HashMap<>();

  UserGroupAggregator() {}

  public UserGroupAggregator(Application application) {
    this.application = application;
  }

  public void addUserGroup(String name) {

  }

  public boolean containsUserGroup(Long id) {
    return false;
  }

  public void removeUserGroup(Long id) {

  }

  public Application getApplication() {
    return application;
  }

  public void setApplication(Application application) {
    this.application = application;
  }

  public Map<Long, UserGroup> getUserGroups() {
    return Map.copyOf(userGroups);
  }

  void setUserGroups(
      Map<Long, UserGroup> userGroups) {
    this.userGroups = userGroups;
  }
}
