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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.validation.ValidationException;
import uk.co.telegraph.switchboard.services.IdGenerator;
import uk.co.telegraph.switchboard.services.PasswordGenerator;

@ExtendWith(MockitoExtension.class)
class ApplicationFactoryTest {

  private static final String APPLICATION_NAME = "Website";
  private static final String APPLICATION_DESCRIPTION = "Company website";

  private static final String APPLICATION_ID = "2c28a57d-46af-46ef-a424-e825e1e27f98";
  private static final String APPLICATION_SECRET = "tncXACXVNL3n5wGM";

  @Mock
  private IdGenerator idGenerator;

  @Mock
  private PasswordGenerator passwordGenerator;

  @InjectMocks
  private ApplicationFactory applicationFactory;

  @Test
  void should_create_an_application_with_id_name_and_password() {
    when(idGenerator.generateId(APPLICATION_NAME)).thenReturn(APPLICATION_ID);
    when(passwordGenerator.generatePassword(16)).thenReturn(APPLICATION_SECRET);

    Application application = applicationFactory.createApplication(APPLICATION_NAME);

    assertThat(application)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("description", null)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }

  @Test
  void should_create_an_application_with_id_name_description_and_password() {
    when(idGenerator.generateId(APPLICATION_NAME)).thenReturn(APPLICATION_ID);
    when(passwordGenerator.generatePassword(16)).thenReturn(APPLICATION_SECRET);

    Application application =
        applicationFactory.createApplication(APPLICATION_NAME, APPLICATION_DESCRIPTION);

    assertThat(application)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET);
  }

  @Test
  void should_trigger_exception_if_application_validation_fails() {
    assertThatThrownBy(() -> applicationFactory.createApplication("  "))
        .isInstanceOf(ValidationException.class);
  }
}
