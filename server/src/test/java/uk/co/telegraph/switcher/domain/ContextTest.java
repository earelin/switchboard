package uk.co.telegraph.switcher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.telegraph.switcher.domain.Context.DEFAULT_KEY;

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

class ContextTest {

  private static final String KEY = "production";

  private static ValidatorFactory validatorFactory;

  private Context context;
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
    context = new Context(KEY);

    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldSetKeyOnConstructor() {
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
    Context comparedObject = new Context("other-context");

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
    context = new Context(DEFAULT_KEY);

    assertThat(context.isDefault())
        .isTrue();
  }

  @Test
  void shouldIsDefaultReturnFalseIfKeyIsNotDefault() {
    assertThat(context.isDefault())
        .isFalse();
  }

  @Test
  void shouldBeEqualToAnContextWithSameKey() {
    Context compareObject = new Context(KEY);

    assertThat(context)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAnContextWithADifferentKey() {
    Context compareObject = new Context("other-context");

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
  void twoObjectWithTheSameKeyShouldHaveSameHashCode() {
    Context compareObject = new Context(KEY);

    assertThat(context.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    Context compareObject = new Context("other-context");

    assertThat(context.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(context.toString())
        .isEqualTo("Context("
            + "key=" + KEY
            + ")");
  }

  @Test
  void shouldValidate() {
    Set<ConstraintViolation<Context>> violations
        = validator.validate(context);

    assertThat(violations)
        .isEmpty();
  }

  @Test
  void shouldNotValidateKeyWithLengthMoreThan64Characters() {
    final String longKey = RandomStringUtils.random(80);

    context = new Context(longKey);

    Set<ConstraintViolation<Context>> violations
        = validator.validate(context);

    assertThat(violations)
        .hasSize(1);

    ConstraintViolation<Context> violation
        = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString())
        .isEqualTo("key");
    assertThat(violation.getInvalidValue())
        .isEqualTo(longKey);
  }

  @Test
  void shouldNotValidateBlankKey() {
    context = new Context("   ");

    Set<ConstraintViolation<Context>> violations
        = validator.validate(context);

    assertThat(violations)
        .hasSize(1);

    ConstraintViolation<Context> violation
        = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString())
        .isEqualTo("key");
    assertThat(violation.getInvalidValue())
        .isEqualTo("   ");
  }
}
