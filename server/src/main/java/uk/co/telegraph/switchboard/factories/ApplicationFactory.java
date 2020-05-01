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

package uk.co.telegraph.switchboard.factories;

import static uk.co.telegraph.switchboard.domain.Application.SECRET_MAX_LENGTH;

import org.springframework.stereotype.Component;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.services.IdGenerator;
import uk.co.telegraph.switchboard.services.PasswordGenerator;

/** Creates new application objects applying invariants. */
@Component
public class ApplicationFactory {

  private final IdGenerator idGenerator;
  private final PasswordGenerator passwordGenerator;

  public ApplicationFactory(IdGenerator idGenerator, PasswordGenerator passwordGenerator) {
    this.idGenerator = idGenerator;
    this.passwordGenerator = passwordGenerator;
  }

  public Application createApplication(String name) {
    return createApplication(name, null);
  }

  public Application createApplication(String name, String description) {
    String id = idGenerator.generateId(name);
    String secret = passwordGenerator.generatePassword(SECRET_MAX_LENGTH);

    Application application = new Application(id, name, secret);
    application.setDescription(description);

    return application;
  }
}
