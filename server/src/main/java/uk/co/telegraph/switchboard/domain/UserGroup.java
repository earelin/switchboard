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
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class UserGroup {

  public static final int NAME_MAX_LENGTH = 32;

  @EqualsAndHashCode.Include
  private String id;
  private Set<String> users = new HashSet<>();

  UserGroup() {}

  /**
   * UserGroup public constructor.
   * @param id ID.
   * @param name Name.
   */
  public UserGroup(String id, String name) {

    this.id = id;
  }

  public void addUser(String name) {
    idValidation(name);
    this.users.add(name);
  }

  public void removeUser(String name) {
    this.users.remove(name);
  }

  public boolean containsUser(String name) {
    return this.users.contains(name);
  }

  public String getId() {
    return id;
  }

  void setId(String id) {
    this.id = id;
  }

  void setUsers(Set<String> users) {
    this.users = new HashSet<>(users);
  }

  public Set<String> getUsers() {
    return this.users;
  }

  private void idValidation(String name) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException(
          String.format("User group id cannot be null or empty, current value: %s", name));
    }

    if (name.length() > NAME_MAX_LENGTH) {
      throw new IllegalArgumentException(
          String.format("User group id cannot be longer than %d characters, current value: %s",
              NAME_MAX_LENGTH, name));
    }
  }
}
