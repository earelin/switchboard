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
import lombok.ToString;
import uk.co.telegraph.switchboard.domain.validation.StringFieldValidator;

/** Context aggregator. */
@ToString
public class ContextsAggregator {

  private final StringFieldValidator contextNameValidator;
  private final StringFieldValidator removeContextNameValidator;

  private final Set<String> contexts;

  public ContextsAggregator() {
    this(new HashSet<>());
  }

  public ContextsAggregator(Set<String> contexts) {
    this.contexts = new HashSet<>(contexts);

    this.contextNameValidator = StringFieldValidator.builder()
        .fieldName("Context name")
        .shouldNotBeNull()
        .shouldNotBeBlank()
        .withRegexValidator("^[a-zA-Z0-9\\-]*$")
        .withCustomValidator(
            value -> !this.contexts.contains(value),
            "Current value does already exists")
        .build();

    this.removeContextNameValidator = StringFieldValidator.builder()
        .fieldName("Context name")
        .shouldNotBeNull()
        .shouldNotBeBlank()
        .withRegexValidator("^[a-zA-Z0-9\\-]*$")
        .build();
  }

  public void addContext(String name) {
    contextNameValidator.apply(name);
    contexts.add(name);
  }

  public void removeContext(String name) {
    removeContextNameValidator.apply(name);

    if (!contexts.contains(name)) {
      throw new ObjectDoesNotExists(String.format("A context with name %s does not exists", name));
    }

    contexts.remove(name);
  }

  public Set<String> getContexts() {
    return Set.copyOf(this.contexts);
  }
}
