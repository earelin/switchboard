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
 *
 */

package uk.co.telegraph.switchboard.domain.strategy;

import static uk.co.telegraph.switchboard.domain.strategy.StrategyAggregator.AND;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BinaryOperator;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switchboard.domain.ClientInfo;
import uk.co.telegraph.switchboard.domain.Context;

/**
 * Set of strategies applicable to a particular context.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StrategySet {
  private StrategyAggregator aggregator = StrategyAggregator.OR;

  private Set<Strategy> strategies = new HashSet<>();

  public boolean isFeatureEnabledForClient(ClientInfo clientInfo) {
    return strategies.stream()
      .map(strategy -> strategy.isFeatureEnabledForClient(clientInfo))
      .reduce(strategyAggregatorFunction(aggregator))
      .orElse(false);
  }

  private BinaryOperator<Boolean> strategyAggregatorFunction(
      StrategyAggregator strategyAggregator) {
    if (strategyAggregator == AND) {
      return Boolean::logicalAnd;
    }
    return Boolean::logicalOr;
  }
}
