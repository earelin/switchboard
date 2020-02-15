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

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Group of users to be used in group related strategies.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserGroup {
  @EqualsAndHashCode.Include
  private final String key;

  private String name;

  private Set<String> users = new HashSet<>();

  public boolean hasUser(String user) {
    return users.contains(user);
  }
}
