package uk.co.telegraph.switcher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnvironmentTest {

  private static final Long ENVIRONMENT_ID = 25L;
  private static final String ENVIRONMENT_KEY = "production";
  private static final String ENVIRONMENT_DESCRIPTION = "Production environment";

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
    environment = new Environment();
    environment.setId(ENVIRONMENT_ID);
    environment.setApplication(createApplication());
    environment.setKey(ENVIRONMENT_KEY);
    environment.setDescription(ENVIRONMENT_DESCRIPTION);

    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldSetId() {
    assertEquals(ENVIRONMENT_ID, environment.getId());
  }

  @Test
  void shouldSetApplication() {
    Application application = createApplication();

    environment.setApplication(application);

    assertThat(environment.getApplication())
        .isEqualTo(application);
  }

  @Test
  void shouldSetKey() {
    assertEquals(ENVIRONMENT_KEY, environment.getKey());
  }

  @Test
  void shouldSetDescription() {
    assertThat(environment.getDescription())
        .isEqualTo(ENVIRONMENT_DESCRIPTION);
  }

  @Test
  void shouldCanEqualSameClass() {
    Environment comparedObject = new Environment();

    assertThat(environment.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "asdf";

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
  void shouldBeEqualToAnEnvironmentWithSameId() {
    Environment compareObject = new Environment();
    compareObject.setId(ENVIRONMENT_ID);

    assertThat(environment)
        .isEqualTo(environment);
  }

  @Test
  void shouldNotBeEqualToAnEnvironmentWithADifferentId() {
    Environment compareObject = new Environment();
    compareObject.setId(12L);
    compareObject.setApplication(createApplication());
    compareObject.setKey(ENVIRONMENT_KEY);
    compareObject.setDescription(ENVIRONMENT_DESCRIPTION);

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
  void twoObjectWithTheSameIdShouldHaveSameHashCode() {
    Environment compareObject = new Environment();
    compareObject.setId(ENVIRONMENT_ID);

    assertThat(environment.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    Environment compareObject = new Environment();
    compareObject.setId(12L);

    assertThat(environment.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(environment.toString())
        .isEqualTo("Environment("
            + "id=" + ENVIRONMENT_ID + ", "
            + "application=" + environment.getApplication().toString() + ", "
            + "key=" + ENVIRONMENT_KEY + ", "
            + "description=" + ENVIRONMENT_DESCRIPTION
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
  void shouldNotValidateKeyWithLengthMoreThan32() {
    final String longKey = "lorem-ipsum-dolor-sit-amet-consectetur-adipiscing-elit-praesent-quam";

    environment.setKey(longKey);

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
    environment.setKey("   ");

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

  private Application createApplication() {
    Application application = new Application();
    application.setId(25L);
    application.setName("Newsroom Dashboard");
    application.setKey("newsroom-dashboard");
    application.setSecret("K2I1JPxYp1pCWprzf4QaReiwntZXxmu4");
    application.setDescription("An amazing application to be feature flagged");

    return application;
  }

}
