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
import uk.co.telegraph.switchboard.domain.validation.StringFieldValidator;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Application {

  public static final int ID_MAX_LENGTH = 36;
  public static final int NAME_MAX_LENGTH = 64;
  public static final int SECRET_MAX_LENGTH = 16;

  private static final StringFieldValidator idValidator = StringFieldValidator.builder()
      .fieldName("id")
      .shouldNotBeNull()
      .shouldNotBeBlank()
      .shouldNotBeLongerThan(ID_MAX_LENGTH)
      .build();

  private static final StringFieldValidator nameValidator = StringFieldValidator.builder()
      .fieldName("name")
      .shouldNotBeNull()
      .shouldNotBeBlank()
      .shouldNotBeLongerThan(NAME_MAX_LENGTH)
      .build();

  private static final StringFieldValidator secretValidator = StringFieldValidator.builder()
      .fieldName("secret")
      .shouldNotBeNull()
      .shouldNotBeBlank()
      .shouldNotBeLongerThan(SECRET_MAX_LENGTH)
      .build();

  @EqualsAndHashCode.Include
  private String id;
  private String name;
  private String description;
  private String secret;

  public Application(String id, String name, String secret) {
    idValidator.apply(id);
    nameValidator.apply(name);
    secretValidator.apply(secret);
    this.id = id;
    this.name = name;
    this.secret = secret;
  }

  public String getId() {
    return id;
  }

  void setId(String id) {
    idValidator.apply(id);
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    nameValidator.apply(name);
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
    secretValidator.apply(secret);
    this.secret = secret;
  }
}
