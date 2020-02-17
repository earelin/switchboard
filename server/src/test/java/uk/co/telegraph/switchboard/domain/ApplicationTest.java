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

import java.util.Set;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationTest {

  private static final Long ID = 15L;
  private static final String KEY = "newsroom-dashboard";
  private static final String NAME = "Newsroom Dashboard";
  private static final String SECRET = "K2I1JPxYp1pCWprzf4QaReiwntZXxmu4";
  private static final String DESCRIPTION
      = "An amazing application to be feature flagged";
  private static final Set<Context> CONTEXTS = generateContexts();

  private Application application;

  @BeforeEach
  void setUp() {
    application = new Application();
    application.setId(ID);
    application.setKey(KEY);
    application.setName(NAME);
    application.setSecret(SECRET);
    application.setDescription(DESCRIPTION);
    application.setContexts(CONTEXTS);
  }

  @Test
  void shouldSetId() {
    assertThat(application.getId())
        .isEqualTo(ID);
  }

  @Test
  void shouldSetKey() {
    assertThat(application.getKey())
        .isEqualTo(KEY);
  }

  @Test
  void shouldSetName() {
    assertThat(application.getName())
        .isEqualTo(NAME);
  }

  @Test
  void shouldSetSecret() {
    assertThat(application.getSecret())
        .isEqualTo(SECRET);
  }

  @Test
  void shouldSetDescription() {
    assertThat(application.getDescription())
        .isEqualTo(DESCRIPTION);
  }

  @Test
  void shouldSetContext() {
    assertThat(application.getContexts())
        .isEqualTo(CONTEXTS);
  }

  @Test
  void shouldCanEqualSameClass() {
    Application comparedObject = new Application();

    assertThat(application.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "string";

    assertThat(application.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldNotCanEqualToNull() {
    assertThat(application.canEqual(null))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(application)
        .isEqualTo(application);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(application)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToAnApplicationWithSameId() {
    Application compareObject = new Application();
    compareObject.setId(ID);

    assertThat(application)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAnApplicationWithADifferentKey() {
    Application compareObject = new Application();
    compareObject.setId(1L);
    compareObject.setKey(KEY);
    compareObject.setName(NAME);
    compareObject.setSecret(SECRET);
    compareObject.setDescription(DESCRIPTION);

    assertThat(application)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(application)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameInstanceShouldHaveSameHashCode() {
    assertThat(application.hashCode())
        .isEqualTo(application.hashCode());
  }

  @Test
  void twoObjectsWithTheSameIdShouldHaveSameHashCode() {
    Application compareObject = new Application();
    compareObject.setId(ID);

    assertThat(application.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectsWithDifferentIdShouldHaveDifferentHashCode() {
    Application compareObject = new Application();
    compareObject.setId(1L);
    compareObject.setKey(KEY);
    compareObject.setName(NAME);
    compareObject.setSecret(SECRET);
    compareObject.setDescription(DESCRIPTION);

    assertThat(application.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(application.toString())
        .startsWith("Application");
  }

  private static Set<Context> generateContexts() {
    Context staging = new Context();
    staging.setId(1L);
    staging.setKey("staging");

    Context production = new Context();
    production.setId(2L);
    production.setKey("production");

    return Set.of(staging, production);
  }
}
