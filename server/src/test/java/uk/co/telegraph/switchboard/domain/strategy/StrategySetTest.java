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

package uk.co.telegraph.switchboard.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.Context;

@ExtendWith(MockitoExtension.class)
class StrategySetTest {

  private static final Long ID = 25L;
  private static final Context CONTEXT = generateContext();

  private StrategySet strategySet;

  @Mock
  private Strategy strategy1;

  @Mock
  private Strategy strategy2;

  @Mock
  private Strategy strategy3;

  @BeforeEach
  void setUp() {
    strategySet = new StrategySet();
    strategySet.setId(ID);
    strategySet.setContext(CONTEXT);
    strategySet.setAggregator(StrategyAggregator.AND);
    strategySet.setStrategies(generateStrategies());
  }

  @Test
  void shouldSetId() {
    assertThat(strategySet.getId())
        .isEqualTo(ID);
  }

  @Test
  void shouldSetContext() {
    assertThat(strategySet.getContext())
        .isEqualTo(CONTEXT);
  }

  @Test
  void shouldSetAggregator() {
    assertThat(strategySet.getAggregator())
        .isEqualTo(StrategyAggregator.AND);
  }

  @Test
  void shouldSetStrategies() {
    assertThat(strategySet.getStrategies())
        .isEqualTo(generateStrategies());
  }

  @Test
  void shouldBeEnabledWithAndAggregatorAndAllEnabledStrategies() {
    when(strategy1.isFeatureEnabledForClient(any())).thenReturn(true);
    when(strategy2.isFeatureEnabledForClient(any())).thenReturn(true);
    when(strategy3.isFeatureEnabledForClient(any())).thenReturn(true);

    assertThat(strategySet.isFeatureEnabledForClient(null))
        .isTrue();
  }

  @Test
  void shouldNotBeEnabledWithAndAggregatorAndOneNotEnabledStrategy() {
    when(strategy1.isFeatureEnabledForClient(any())).thenReturn(true);
    when(strategy2.isFeatureEnabledForClient(any())).thenReturn(false);
    when(strategy3.isFeatureEnabledForClient(any())).thenReturn(true);

    assertThat(strategySet.isFeatureEnabledForClient(null))
        .isFalse();
  }

  @Test
  void shouldBeEnabledWithAndAggregatorOrAndOneEnabledStrategies() {
    strategySet.setAggregator(StrategyAggregator.OR);
    when(strategy1.isFeatureEnabledForClient(any())).thenReturn(false);
    when(strategy2.isFeatureEnabledForClient(any())).thenReturn(true);
    when(strategy3.isFeatureEnabledForClient(any())).thenReturn(false);

    assertThat(strategySet.isFeatureEnabledForClient(null))
        .isTrue();
  }

  @Test
  void shouldNotBeEnabledWithAndAggregatorOrAndAllStrategiesNotEnabled() {
    strategySet.setAggregator(StrategyAggregator.OR);
    when(strategy1.isFeatureEnabledForClient(any())).thenReturn(false);
    when(strategy2.isFeatureEnabledForClient(any())).thenReturn(false);
    when(strategy3.isFeatureEnabledForClient(any())).thenReturn(false);

    assertThat(strategySet.isFeatureEnabledForClient(null))
        .isFalse();
  }

  @Test
  void shouldNotBeEnabledWithEmptyStrategies() {
    strategySet.setStrategies(Set.of());

    assertThat(strategySet.isFeatureEnabledForClient(null))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    StrategySet comparedObject = new StrategySet();

    assertThat(strategySet.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "string";

    assertThat(strategySet.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldNotCanEqualToNull() {
    assertThat(strategySet.canEqual(null))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(strategySet)
        .isEqualTo(strategySet);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(strategySet)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToADefaultStrategyWithSameId() {
    StrategySet compareObject = new StrategySet();
    compareObject.setId(ID);

    assertThat(strategySet)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADefaultStrategyWithADifferentId() {
    StrategySet compareObject = new StrategySet();
    compareObject.setId(12L);
    compareObject.setAggregator(StrategyAggregator.AND);
    compareObject.setStrategies(generateStrategies());

    assertThat(strategySet)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(strategySet)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameInstanceShouldHaveSameHashCode() {
    assertThat(strategySet.hashCode())
        .isEqualTo(strategySet.hashCode());
  }

  @Test
  void twoApplicationsWithTheSameIdShouldHaveSameHashCode() {
    StrategySet compareObject = new StrategySet();
    compareObject.setId(ID);
    compareObject.setAggregator(StrategyAggregator.AND);
    compareObject.setStrategies(generateStrategies());

    assertThat(strategySet.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    StrategySet compareObject = new StrategySet();
    compareObject.setId(12L);

    assertThat(strategySet.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(strategySet.toString())
        .startsWith("StrategySet");
  }

  private static Context generateContext() {
    Context context = new Context();
    context.setId(12L);
    context.setKey("production");
    return context;
  }

  private Set<Strategy> generateStrategies() {
    return Set.of(strategy1, strategy2, strategy3);
  }

}
