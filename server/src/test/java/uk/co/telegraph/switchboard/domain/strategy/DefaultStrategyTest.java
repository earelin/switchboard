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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultStrategyTest {

  private static final Long ID = 25L;

  private DefaultStrategy defaultStrategy;

  @BeforeEach
  void setUp() {
    defaultStrategy = new DefaultStrategy();
    defaultStrategy.setId(ID);
    defaultStrategy.setEnabled(true);
  }

  @Test
  void shouldSetId() {
    assertThat(defaultStrategy.getId())
        .isEqualTo(ID);
  }

  @Test
  void shouldBeEnabledIfEnabledIsTrue() {
    assertThat(defaultStrategy.isFeatureEnabledForClient(null))
        .isTrue();
  }

  @Test
  void shouldNotBeEnabledIfEnabledIsFalse() {
    defaultStrategy.setEnabled(false);

    assertThat(defaultStrategy.isFeatureEnabledForClient(null))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    DefaultStrategy comparedObject = new DefaultStrategy();

    assertThat(defaultStrategy.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "string";

    assertThat(defaultStrategy.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldNotCanEqualToNull() {
    assertThat(defaultStrategy.canEqual(null))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(defaultStrategy)
        .isEqualTo(defaultStrategy);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(defaultStrategy)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToADefaultStrategyWithSameId() {
    DefaultStrategy compareObject = new DefaultStrategy();
    compareObject.setId(ID);

    assertThat(defaultStrategy)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADefaultStrategyWithADifferentId() {
    DefaultStrategy compareObject = new DefaultStrategy();
    compareObject.setId(12L);
    compareObject.setEnabled(true);

    assertThat(defaultStrategy)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(defaultStrategy)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameInstanceShouldHaveSameHashCode() {
    assertThat(defaultStrategy.hashCode())
        .isEqualTo(defaultStrategy.hashCode());
  }

  @Test
  void twoApplicationsWithTheSameIdShouldHaveSameHashCode() {
    DefaultStrategy compareObject = new DefaultStrategy();
    compareObject.setId(ID);

    assertThat(defaultStrategy.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    DefaultStrategy compareObject = new DefaultStrategy();
    compareObject.setId(12L);
    compareObject.setEnabled(true);

    assertThat(defaultStrategy.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(defaultStrategy.toString())
        .startsWith("DefaultStrategy");
  }
}
