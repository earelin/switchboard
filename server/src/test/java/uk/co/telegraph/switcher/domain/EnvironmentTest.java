package uk.co.telegraph.switcher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.telegraph.switcher.domain.Environment.DEFAULT_ENVIRONMENT_KEY;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnvironmentTest {

  private static final String KEY = "production";

  private static ValidatorFactory validatorFactory;

  private Environment environment;
  private Validator validator;

  @BeforeAll
  static void init() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
  }

  @AfterAll
  static void finish() {
    validatorFactory.close();
  }

  @BeforeEach
  void setUp() {
    environment = new Environment(KEY);

    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldSetKeyOnConstructor() {
    assertThat(environment.getKey())
        .isEqualTo(KEY);
  }

  @Test
  void shouldGenerateADefaultEnvironment() {
    environment = Environment.buildDefault();

    assertThat(environment.isDefault())
        .isTrue();
  }

  @Test
  void shouldCanEqualSameClass() {
    Environment comparedObject = new Environment("other-environment");

    assertThat(environment.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "some string";

    assertThat(environment.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(environment)
        .isEqualTo(environment);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(environment)
        .isNotEqualTo(null);
  }

  @Test
  void shouldIsDefaultReturnTrueIfKeyIsDefault() {
    environment = new Environment(DEFAULT_ENVIRONMENT_KEY);

    assertThat(environment.isDefault())
        .isTrue();
  }

  @Test
  void shouldIsDefaultReturnFalseIfKeyIsNotDefault() {
    assertThat(environment.isDefault())
        .isFalse();
  }

  @Test
  void shouldBeEqualToAnEnvironmentWithSameKey() {
    Environment compareObject = new Environment(KEY);

    assertThat(environment)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAnEnvironmentWithADifferentKey() {
    Environment compareObject = new Environment("other-environment");

    assertThat(environment)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(environment)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    assertThat(environment.hashCode())
        .isEqualTo(environment.hashCode());
  }

  @Test
  void twoObjectWithTheSameKeyShouldHaveSameHashCode() {
    Environment compareObject = new Environment(KEY);

    assertThat(environment.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    Environment compareObject = new Environment("other-environment");

    assertThat(environment.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(environment.toString())
        .isEqualTo("Environment("
            + "key=" + KEY
            + ")");
  }

  @Test
  void shouldValidate() {
    Set<ConstraintViolation<Environment>> violations
        = validator.validate(environment);

    assertThat(violations)
        .isEmpty();
  }

  @Test
  void shouldNotValidateKeyWithLengthMoreThan64Characters() {
    final String longKey = RandomStringUtils.random(80);

    environment = new Environment(longKey);

    Set<ConstraintViolation<Environment>> violations
        = validator.validate(environment);

    assertThat(violations)
        .hasSize(1);

    ConstraintViolation<Environment> violation
        = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString())
        .isEqualTo("key");
    assertThat(violation.getInvalidValue())
        .isEqualTo(longKey);
  }

  @Test
  void shouldNotValidateBlankKey() {
    environment = new Environment("   ");

    Set<ConstraintViolation<Environment>> violations
        = validator.validate(environment);

    assertThat(violations)
        .hasSize(1);

    ConstraintViolation<Environment> violation
        = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString())
        .isEqualTo("key");
    assertThat(violation.getInvalidValue())
        .isEqualTo("   ");
  }
}
