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

package uk.co.telegraph.switchboard.domain;

import java.util.Map;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * An application is a namespace that represents a particular software artifact
 * that will have its own set of contexts and feature flags.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application {
  @EqualsAndHashCode.Include
  private String key;
  private String name;
  private String secret;
  private String description;
  private Set<Context> contexts;
  private Map<String, FeatureFlag> featureFlags;

  public Application() {
  }

  public Application(
      String key,
      String name,
      String secret,
      String description
  ) {
    this.key = key;
    this.name = name;
    this.secret = secret;
    this.description = description;
  }
}
