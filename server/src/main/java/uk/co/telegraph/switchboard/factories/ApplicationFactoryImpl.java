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

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.Context;
import uk.co.telegraph.switchboard.services.KeyGeneratorService;
import uk.co.telegraph.switchboard.services.SecretsGeneratorService;

@Component
public class ApplicationFactoryImpl implements ApplicationFactory {

  public static final int SECRET_SIZE = 64;

  private final KeyGeneratorService keyGeneratorService;
  private final SecretsGeneratorService secretsGeneratorService;

  public ApplicationFactoryImpl(KeyGeneratorService keyGeneratorService,
      SecretsGeneratorService secretsGeneratorService) {
    this.keyGeneratorService = keyGeneratorService;
    this.secretsGeneratorService = secretsGeneratorService;
  }

  @Override
  public Application createWith(String name, String description) {
    return createWith(name, description, Set.of(Context.DEFAULT_KEY));
  }

  @Override
  public Application createWith(String name, String description, Set<String> contexts) {
    checkNameArgument(name);
    checkContextsArgument(contexts);

    Application application = new Application();
    application.setId(keyGeneratorService.generateKey(name));
    application.setSecret(secretsGeneratorService.generateSecret(SECRET_SIZE));
    application.setName(name);
    application.setDescription(description);
    application.setContexts(generateContextSetFromKeys(contexts));

    return application;
  }

  private Set<Context> generateContextSetFromKeys(Set<String> keys) {
    return keys.stream()
        .map(key -> new Context(key))
        .collect(Collectors.toSet());
  }

  private void checkNameArgument(String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException("Name argument must not be empty or null");
    }
  }

  private void checkContextsArgument(Set<String> contexts) {
    if (Objects.isNull(contexts) || contexts.isEmpty()) {
      throw new IllegalArgumentException("Context argument must not be empty or null");
    }
  }
}
