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

import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ClientInfo {
  private final String application;
  private final ZonedDateTime time;
  private final Map<String, String> properties = new HashMap<>();

  public ClientInfo(String application, ZonedDateTime time) {
    this.application = application;
    this.time = time;
  }

  public String getApplication() {
    return application;
  }

  public ZonedDateTime getTime() {
    return time;
  }

  public Optional<String> getPropertyValue(String key) {
    return Optional.ofNullable(properties.get(key));
  }

  public void setProperty(String key, String value) {
    properties.put(key, value);
  }

  public boolean doesPropertyExists(String key) {
    return properties.containsKey(key);
  }

  public Set<String> getPropertyKeys() {
    return properties.keySet();
  }

  public Map<String, String> getProperties() {
    return Map.copyOf(properties);
  }
}
