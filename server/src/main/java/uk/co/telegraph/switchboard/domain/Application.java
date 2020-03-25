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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Application {

  @Id
  @EqualsAndHashCode.Include
  @Column(columnDefinition = "char(36)", length = 36)
  private String id;

  @Column(length = 64)
  private String name;

  @Column(columnDefinition = "longtext")
  private String description;

  @Column(columnDefinition = "char(12)", length = 12)
  private String secret;

  Application() {
  }

  public Application(String id, String name, String secret) {
    setId(id);
    setName(name);
    setSecret(secret);
  }

  public String getId() {
    return id;
  }

  void setId(String id) {
    if (StringUtils.isBlank(id)) {
      throw new IllegalArgumentException("Application id cannot be empty or null");
    }

    if (id.length() > 36) {
      throw new IllegalArgumentException("Application id cannot be longer than 36 characters");
    }

    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("Application name cannot be empty or null");
    }

    if (name.length() > 64) {
      throw new IllegalArgumentException("Application name cannot be longer than 64 characters");
    }

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
    if (StringUtils.isBlank(secret)) {
      throw new IllegalArgumentException("Application secret cannot be empty or null");
    }

    if (secret.length() > 16) {
      throw new IllegalArgumentException("Application secret cannot be longer than 16 characters");
    }

    this.secret = secret;
  }
}
