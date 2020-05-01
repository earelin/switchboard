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
public class Application {

  public static final int ID_MAX_LENGTH = 36;
  public static final int NAME_MAX_LENGTH = 64;
  public static final int SECRET_MAX_LENGTH = 16;

  @EqualsAndHashCode.Include
  private String id;
  private String name;
  private String description;
  private String secret;

  public Application(String id, String name, String secret) {
    idValidation(id);
    nameValidation(name);
    secretValidation(secret);
    this.id = id;
    this.name = name;
    this.secret = secret;
  }

  public String getId() {
    return id;
  }

  void setId(String id) {
    idValidation(id);
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    nameValidation(name);
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    secretValidation(secret);
    this.secret = secret;
  }

  private void idValidation(String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("Application id cannot be empty or null");
    }

    if (id.length() > ID_MAX_LENGTH) {
      throw new IllegalArgumentException(
          String.format("Application id cannot be longer than %d characters", ID_MAX_LENGTH));
    }
  }

  private void nameValidation(String name) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("Application name cannot be empty or null");
    }

    if (name.length() > NAME_MAX_LENGTH) {
      throw new IllegalArgumentException(
          String.format("Application name cannot be longer than %d characters", NAME_MAX_LENGTH));
    }
  }

  private void secretValidation(String secret) {
    if (StringUtils.isBlank(secret)) {
      throw new IllegalArgumentException("Application secret cannot be empty or null");
    }

    if (secret.length() > SECRET_MAX_LENGTH) {
      throw new IllegalArgumentException(
          String.format("Application secret cannot be longer than %d characters",
              SECRET_MAX_LENGTH));
    }
  }
}
