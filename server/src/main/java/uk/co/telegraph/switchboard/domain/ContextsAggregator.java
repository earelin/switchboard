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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/** Context aggregator. */
public class ContextsAggregator {
  private Application application;
  private Map<String, Context> contexts = new HashMap<>();

  ContextsAggregator() {}

  public ContextsAggregator(Application application) {
    this.application = application;
  }

  public void addContext(String name) {
    if (Objects.isNull(name)) {
      throw new NullPointerException("Not allowed null value on context name");
    }

    if (StringUtils.isBlank(name)) {
      throw new IllegalArgumentException("Not allowed empty string value on context name");
    }

    if (contexts.containsKey(name)) {
      throw new ObjectAlreadyExistsInAggregate(
          String.format("A context with the name %s already exists", name));
    }

    Context context = new Context(name);

    contexts.put(name, context);
  }

  public boolean containsContext(String name) {
    return contexts.containsKey(name);
  }

  public Optional<Context> getContext(String name) {
    if (Objects.isNull(name)) {
      throw new NullPointerException("Not allowed null value on context name");
    }

    return Optional.ofNullable(contexts.get(name));
  }

  public void removeContext(String name) {
    if (Objects.isNull(name)) {
      throw new NullPointerException("Could not remove a null context");
    }

    if (!contexts.containsKey(name)) {
      throw new ObjectDoesNotExists(String.format("A context with name %s does not exists", name));
    }

    contexts.remove(name);
  }

  public Application getApplication() {
    return this.application;
  }

  void setApplication(Application application) {
    this.application = application;
  }

  public Map<String, Context> getContexts() {
    return Map.copyOf(this.contexts);
  }

  void setContexts(Map<String, Context> contexts) {
    this.contexts = contexts;
  }
}
