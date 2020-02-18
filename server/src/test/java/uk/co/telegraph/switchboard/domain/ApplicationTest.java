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
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ApplicationTest {

  private static final String ID = "c0a8e1e5-e307-3c5b-b381-9b387b5f01fd";
  private static final String NAME = "Newsroom Dashboard";
  private static final String SECRET = "K2I1JPxYp1pCWprzf4QaReiwntZXxmu4";
  private static final String DESCRIPTION
      = "An amazing application to be feature flagged";

  private static final ValidatorFactory validatorFactory
      = Validation.buildDefaultValidatorFactory();

  private Application application;
  private Validator validator;
  private Set<Context> contexts;

  @BeforeEach
  void setUp() {
    contexts = generateContexts(application);

    application = new Application();
    application.setId(ID);
    application.setName(NAME);
    application.setSecret(SECRET);
    application.setDescription(DESCRIPTION);
    application.setContexts(contexts);

    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldValidateApplication() {
    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertThat(violations)
        .isEmpty();
  }

  @Test
  void shouldSetId() {
    assertThat(application.getId())
        .isEqualTo(ID);
  }

  @Test
  void shouldNotValidateNullId() {
    application.setId(null);

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "id");

    assertThat(violations)
        .hasSize(1);
  }

  @Test
  void shouldNotValidateBlankKey() {
    application.setId("    ");

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "id");

    assertThat(violations)
        .hasSize(1);
  }

  @ParameterizedTest
  @ValueSource(ints = {129, 256, 512})
  void shouldNotValidateKeyLongerThan128Chars(int keyLength) {
    application.setId(RandomStringUtils.randomAlphanumeric(keyLength));

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "id");

    assertThat(violations)
        .hasSize(1);
  }

  @Test
  void shouldSetName() {
    assertThat(application.getName())
        .isEqualTo(NAME);
  }

  @Test
  void shouldNotValidateNullName() {
    application.setName(null);

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "name");

    assertThat(violations)
        .hasSize(1);
  }

  @Test
  void shouldNotValidateBlankName() {
    application.setName("    ");

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "name");

    assertThat(violations)
        .hasSize(1);
  }

  @ParameterizedTest
  @ValueSource(ints = {129, 256, 512})
  void shouldNotValidateNameLongerThan128Chars(int nameLength) {
    application.setName(RandomStringUtils.randomAlphanumeric(nameLength));

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "name");

    assertThat(violations)
        .hasSize(1);
  }

  @Test
  void shouldSetSecret() {
    assertThat(application.getSecret())
        .isEqualTo(SECRET);
  }

  @Test
  void shouldNotValidateNullSecret() {
    application.setSecret(null);

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "secret");

    assertThat(violations)
        .hasSize(1);
  }

  @Test
  void shouldNotValidateBlankSecret() {
    application.setSecret("    ");

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "secret");

    assertThat(violations)
        .hasSize(1);
  }

  @ParameterizedTest
  @ValueSource(ints = {256, 512, 1024})
  void shouldNotValidateSecretLongerThan255Chars(int nameLength) {
    application.setSecret(RandomStringUtils.randomAlphanumeric(nameLength));

    Set<ConstraintViolation<Application>> violations
        = validator.validateProperty(application, "secret");

    assertThat(violations)
        .hasSize(1);
  }

  @Test
  void shouldSetDescription() {
    assertThat(application.getDescription())
        .isEqualTo(DESCRIPTION);
  }

  @Test
  void shouldSetContext() {
    assertThat(application.getContexts())
        .isEqualTo(contexts);
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
  void shouldNotBeEqualToAnApplicationWithADifferentId() {
    Application compareObject = new Application();
    compareObject.setId("d2270af7-0528-38c0-94d2-5515c0156e8a");
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
    compareObject.setId("d2270af7-0528-38c0-94d2-5515c0156e8a");
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

  private Set<Context> generateContexts(Application application) {
    Context staging = new Context();
    staging.setKey("staging");

    Context production = new Context();
    production.setKey("production");

    return Set.of(staging, production);
  }
}
