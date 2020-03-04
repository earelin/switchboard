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
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application {

  @EqualsAndHashCode.Include
  private String id;
  private String name;
  private String description;
  private Map<String, FeatureFlag> featureFlags = new HashMap<>();

  public Application() {
  }

  public Application(String id) {
    this.id = id;
  }

  public void addFeatureFlag(FeatureFlag featureFlag) {
    featureFlags.put(featureFlag.getId(), featureFlag);
  }

  public void removeFeatureFlag(FeatureFlag featureFlag) {
    featureFlags.remove(featureFlag.getId());
  }

  public Optional<FeatureFlag> getFeatureFlagById(String id) {
    return Optional.ofNullable(featureFlags.get(id));
  }

  public Map<String, Boolean> getFeatureStatusesForClient(ClientInfo clientInfo) {
    Map<String, Boolean> featureStatuses = new HashMap<>();
    for (FeatureFlag featureFlag : featureFlags.values()) {
      featureStatuses.put(featureFlag.getId(), featureFlag.isEnabledForClient(clientInfo));
    }
    return featureStatuses;
  }

  public Set<FeatureFlag> getFeatureFlags() {
    return new HashSet<>(featureFlags.values());
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
