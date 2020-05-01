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

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Context {
  public static final int NAME_MAX_LENGTH = 32;

  @EqualsAndHashCode.Include
  private String id;
  private String name;

  /**
   * Context public constructor.
   * @param id ID.
   * @param name Name.
   */
  public Context(String id, String name) {
    this.id = id;
    nameValidation(name);
    this.name = name;
  }

  String getId() {
    return id;
  }

  void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    nameValidation(name);
    this.name = name;
  }

  private void nameValidation(String name) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("Context name cannot be null or empty");
    }

    if (name.length() > NAME_MAX_LENGTH) {
      throw new IllegalArgumentException(
          String.format("Context name cannot be longer than %d characters", NAME_MAX_LENGTH));
    }
  }
}
