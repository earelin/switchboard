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
 */

package uk.co.telegraph.switchboard.factories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.Context;
import uk.co.telegraph.switchboard.services.KeyGeneratorService;
import uk.co.telegraph.switchboard.services.SecretsGeneratorService;

@ExtendWith(MockitoExtension.class)
class ApplicationFactoryImplTest {

  private static final String NAME = "My Super Application";
  private static final String DESCRIPTION = "The best application of the world";
  private static final Set<String> CONTEXT = Set.of("preprod", "prod");

  private static final String ID = "c0a8e1e5-e307-3c5b-b381-9b387b5f01fd";
  private static final String SECRET
      = "8XENq5bOYENOAbtDRrz1DQTrZKYmgqYsbQ7GJJ1yIfpwDdZwn5SBQf79R4VyazwA";

  private ApplicationFactory applicationFactory;

  @Mock
  private KeyGeneratorService keyGeneratorService;

  @Mock
  private SecretsGeneratorService secretsGeneratorService;

  @BeforeEach
  void setUp() {
    applicationFactory = new ApplicationFactoryImpl(keyGeneratorService, secretsGeneratorService);
  }

  @Test
  void shouldCreateAnApplicationFromNameAndDescription() {
    when(keyGeneratorService.generateKey(any()))
        .thenReturn(ID);
    when(secretsGeneratorService.generateSecret(anyInt()))
        .thenReturn(SECRET);

    Application application = applicationFactory.createWith(NAME, DESCRIPTION);

    assertThat(application)
        .hasFieldOrPropertyWithValue("id", ID)
        .hasFieldOrPropertyWithValue("name", NAME)
        .hasFieldOrPropertyWithValue("description", DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", SECRET);

    assertThat(application.getContexts())
        .isNotEmpty()
        .extracting(Context::isDefault)
        .contains(true);
  }

  @Test
  void shouldNotCreateAnApplicationFromNameAndDescriptionIfNameIsNull() {
    assertThatThrownBy(() -> applicationFactory.createWith(null, DESCRIPTION))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name argument must not be empty or null");
  }

  @Test
  void shouldNotCreateAnApplicationFromNameAndDescriptionIfNameIsEmpty() {
    assertThatThrownBy(() -> applicationFactory.createWith("   ", DESCRIPTION))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name argument must not be empty or null");
  }

  @Test
  void shouldCreateAnApplicationFromNameDescriptionAndContexts() {
    when(keyGeneratorService.generateKey(any()))
        .thenReturn(ID);
    when(secretsGeneratorService.generateSecret(anyInt()))
        .thenReturn(SECRET);

    Application application = applicationFactory.createWith(NAME, DESCRIPTION, CONTEXT);

    assertThat(application)
        .hasFieldOrPropertyWithValue("id", ID)
        .hasFieldOrPropertyWithValue("name", NAME)
        .hasFieldOrPropertyWithValue("description", DESCRIPTION)
        .hasFieldOrPropertyWithValue("secret", SECRET);

    assertThat(application.getContexts())
        .isNotEmpty()
        .extracting(Context::getKey)
        .contains("preprod", "prod");
  }

  @Test
  void shouldNotCreateAnApplicationFromNameDescriptionAndContextIfNameIsNull() {
    assertThatThrownBy(() -> applicationFactory.createWith(null, DESCRIPTION, CONTEXT))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name argument must not be empty or null");
  }

  @Test
  void shouldNotCreateAnApplicationFromNameDescriptionAndContextIfNameIsEmpty() {
    assertThatThrownBy(() -> applicationFactory.createWith("   ", DESCRIPTION, CONTEXT))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Name argument must not be empty or null");
  }

  @Test
  void shouldNotCreateAnApplicationFromNameDescriptionAndContextIfContextIsNull() {
    assertThatThrownBy(() -> applicationFactory.createWith(NAME, DESCRIPTION, null))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Context argument must not be empty or null");
  }

  @Test
  void shouldNotCreateAnApplicationFromNameDescriptionAndContextIfContextIsEmpty() {
    assertThatThrownBy(() -> applicationFactory.createWith(NAME, DESCRIPTION, Set.of()))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessageContaining("Context argument must not be empty or null");
  }
}
