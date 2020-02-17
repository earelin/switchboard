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

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.telegraph.switchboard.domain.Context.DEFAULT_KEY;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContextTest {

  private static final Long ID = 25L;
  private static final String KEY = "production";

  private Context context;

  @BeforeEach
  void setUp() {
    context = new Context();
    context.setId(ID);
    context.setKey(KEY);
  }

  @Test
  void shouldSetId() {
    assertThat(context.getId())
        .isEqualTo(ID);
  }

  @Test
  void shouldSetKey() {
    assertThat(context.getKey())
        .isEqualTo(KEY);
  }

  @Test
  void shouldGenerateADefaultContext() {
    context = Context.buildDefault();

    assertThat(context.isDefault())
        .isTrue();
  }

  @Test
  void shouldCanEqualSameClass() {
    Context comparedObject = new Context();

    assertThat(context.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "some string";

    assertThat(context.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldNotCanEqualToNull() {
    assertThat(context.canEqual(null))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(context)
        .isEqualTo(context);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(context)
        .isNotEqualTo(null);
  }

  @Test
  void shouldIsDefaultReturnTrueIfKeyIsDefault() {
    context = new Context();
    context.setKey(DEFAULT_KEY);

    assertThat(context.isDefault())
        .isTrue();
  }

  @Test
  void shouldIsDefaultReturnFalseIfKeyIsNotDefault() {
    assertThat(context.isDefault())
        .isFalse();
  }

  @Test
  void shouldBeEqualToAnContextWithSameId() {
    Context compareObject = new Context();
    compareObject.setId(ID);

    assertThat(context)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAnContextWithADifferentKey() {
    Context compareObject = new Context();
    compareObject.setId(15L);
    compareObject.setKey(KEY);

    assertThat(context)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(context)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    assertThat(context.hashCode())
        .isEqualTo(context.hashCode());
  }

  @Test
  void twoObjectWithTheSameIdShouldHaveSameHashCode() {
    Context compareObject = new Context();
    compareObject.setId(ID);

    assertThat(context.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    Context compareObject = new Context();
    compareObject.setId(15L);
    compareObject.setKey(KEY);

    assertThat(context.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(context.toString())
        .startsWith("Context");
  }
}
