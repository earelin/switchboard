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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.strategy.StrategySet;

@ExtendWith(MockitoExtension.class)
class FeatureFlagTest {

  private static final String KEY = "performance.chart";
  private static final String DESCRIPTION = "Performance chart feature";
  private static final Boolean ACTIVE = true;

  @Mock
  private StrategySet strategySetProduction;

  @Mock
  private StrategySet strategySetTesting;

  @Mock
  private ClientInfo clientInfo;

  private FeatureFlag featureFlag;

  @BeforeEach
  void setUp() {
    featureFlag = new FeatureFlag(KEY);
    featureFlag.setDescription(DESCRIPTION);
    featureFlag.setActive(ACTIVE);
    featureFlag.setStrategySets(generateStrategySets());
  }

  @Test
  void shouldSetKey() {
    assertThat(featureFlag.getKey())
        .isEqualTo(KEY);
  }

  @Test
  void shouldSetDescription() {
    assertThat(featureFlag.getDescription())
        .isEqualTo(DESCRIPTION);
  }

  @Test
  void shouldSetActive() {
    assertThat(featureFlag.isActive())
        .isTrue();
  }

  @Test
  void featureIsEnabledIfContextIsDefinedAndStrategySetEvaluatesAsEnabled() {
    when(strategySetProduction.isFeatureEnabled(any())).thenReturn(true);
    when(clientInfo.getContext()).thenReturn(new Context("production"));

    assertThat(featureFlag.isFeatureEnabled(clientInfo))
        .isTrue();
  }

  @Test
  void featureIsNotEnabledIfContextIsDefinedAndStrategySetEvaluatesAsNotEnabled() {
    when(strategySetProduction.isFeatureEnabled(any())).thenReturn(false);
    when(clientInfo.getContext()).thenReturn(new Context("production"));

    assertThat(featureFlag.isFeatureEnabled(clientInfo))
        .isFalse();
  }

  @Test
  void featureIsNotEnabledIfContextIsNotDefined() {
    when(clientInfo.getContext()).thenReturn(new Context("undefined-context"));

    assertThat(featureFlag.isFeatureEnabled(clientInfo))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    FeatureFlag comparedObject = new FeatureFlag("other.feature.flag");

    assertThat(featureFlag.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "asdf";

    assertThat(featureFlag.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(featureFlag)
        .isEqualTo(featureFlag);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(featureFlag)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToAFeatureFlagWithSameKey() {
    FeatureFlag compareObject = new FeatureFlag(KEY);

    assertThat(featureFlag)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAFeatureFlagWithADifferentKey() {
    FeatureFlag compareObject = new FeatureFlag("other.feature.flag");
    featureFlag.setDescription(DESCRIPTION);
    featureFlag.setActive(ACTIVE);

    assertThat(featureFlag)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(featureFlag)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    assertThat(featureFlag.hashCode())
        .isEqualTo(featureFlag.hashCode());
  }

  @Test
  void twoObjectWithTheSameIdShouldHaveSameHashCode() {
    FeatureFlag compareObject = new FeatureFlag(KEY);

    assertThat(featureFlag.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    FeatureFlag compareObject = new FeatureFlag("other.feature.flag");

    assertThat(featureFlag.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(featureFlag.toString())
        .isEqualTo("FeatureFlag("
            + "key=" + KEY + ", "
            + "description=" + DESCRIPTION + ", "
            + "active=" + ACTIVE + ", "
            + "strategySets=" + generateStrategySets()
            + ")");
  }

  private Map<Context, StrategySet> generateStrategySets() {
    return Map.of(
        new Context("production"), strategySetProduction,
        new Context("testing"), strategySetTesting
    );
  }
}
