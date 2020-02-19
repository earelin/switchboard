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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import uk.co.telegraph.switchboard.domain.strategy.StrategySet;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public class FeatureFlag {

  @Id @GeneratedValue
  @EqualsAndHashCode.Include
  private Long id;

  private String key;
  private String description;
  private boolean active = false;

  @OneToMany
  private Map<String, StrategySet> strategySets;

  public boolean isFeatureEnabledForClient(ClientInfo clientInfo) {
    String context = clientInfo.getContext();
    if (strategySets.containsKey(context)) {
      StrategySet strategySet = strategySets.get(context);
      return strategySet.isFeatureEnabledForClient(clientInfo);
    } else {
      log.warn("Not existing context in request: {}", clientInfo);
    }
    return false;
  }
}
