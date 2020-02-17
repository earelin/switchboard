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

  private static final Long ID = 25L;
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
    featureFlag = new FeatureFlag();
    featureFlag.setId(ID);
    featureFlag.setKey(KEY);
    featureFlag.setDescription(DESCRIPTION);
    featureFlag.setActive(ACTIVE);
    featureFlag.setStrategySets(generateStrategySets());
  }

  @Test
  void shouldSetId() {
    assertThat(featureFlag.getId())
        .isEqualTo(ID);
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
  void shouldSetStrategySets() {
    assertThat(featureFlag.getStrategySets())
        .isEqualTo(generateStrategySets());
  }

  @Test
  void featureIsEnabledIfContextIsDefinedAndStrategySetEvaluatesAsEnabled() {
    when(strategySetProduction.isFeatureEnabledForClient(any())).thenReturn(true);
    when(clientInfo.getContext()).thenReturn("production");

    assertThat(featureFlag.isFeatureEnabledForClient(clientInfo))
        .isTrue();
  }

  @Test
  void featureIsNotEnabledIfContextIsDefinedAndStrategySetEvaluatesAsNotEnabled() {
    when(strategySetProduction.isFeatureEnabledForClient(any())).thenReturn(false);
    when(clientInfo.getContext()).thenReturn("production");

    assertThat(featureFlag.isFeatureEnabledForClient(clientInfo))
        .isFalse();
  }

  @Test
  void featureIsNotEnabledIfContextIsNotDefined() {
    when(clientInfo.getContext()).thenReturn("undefined-context");

    assertThat(featureFlag.isFeatureEnabledForClient(clientInfo))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    FeatureFlag comparedObject = new FeatureFlag();

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
  void shouldNotCanEqualToNull() {
    assertThat(featureFlag.canEqual(null))
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
  void shouldBeEqualToAFeatureFlagWithSameId() {
    FeatureFlag compareObject = new FeatureFlag();
    compareObject.setId(ID);

    assertThat(featureFlag)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAFeatureFlagWithADifferentId() {
    FeatureFlag compareObject = new FeatureFlag();
    featureFlag.setId(15L);
    featureFlag.setKey(KEY);
    featureFlag.setDescription(DESCRIPTION);
    featureFlag.setActive(ACTIVE);
    featureFlag.setStrategySets(generateStrategySets());

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
    FeatureFlag compareObject = new FeatureFlag();
    compareObject.setId(ID);

    assertThat(featureFlag.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    FeatureFlag compareObject = new FeatureFlag();
    featureFlag.setId(15L);
    featureFlag.setKey(KEY);
    featureFlag.setDescription(DESCRIPTION);
    featureFlag.setActive(ACTIVE);
    featureFlag.setStrategySets(generateStrategySets());

    assertThat(featureFlag.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(featureFlag.toString())
        .startsWith("FeatureFlag");
  }

  private Map<String, StrategySet> generateStrategySets() {
    return Map.of(
        "production", strategySetProduction,
        "testing", strategySetTesting
    );
  }
}
