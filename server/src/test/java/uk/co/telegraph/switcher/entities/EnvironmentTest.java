package uk.co.telegraph.switcher.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    assertEquals(application, environment.getApplication());
  }

  @Test
  void shouldSetKey() {
    assertEquals(ENVIRONMENT_KEY, environment.getKey());
  }

  @Test
  void shouldSetDescription() {
    assertEquals(ENVIRONMENT_DESCRIPTION, environment.getDescription());
  }

  @Test
  void shouldCanEqualSameClass() {
    Environment comparedObject = new Environment();

    assertTrue(environment.canEqual(comparedObject));
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "asdf";

    assertFalse(environment.canEqual(comparedObject));
  }

  @Test
  void shouldBeEqualToItself() {
    assertEquals(environment, environment);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertNotEquals(null, environment);
  }

  @Test
  void shouldBeEqualToAnEnvironmentWithSameId() {
    Environment compareObject = new Environment();
    compareObject.setId(ENVIRONMENT_ID);

    assertEquals(compareObject, environment);
  }

  @Test
  void shouldNotBeEqualToAnEnvironmentWithADifferentId() {
    Environment compareObject = new Environment();
    compareObject.setId(12L);
    compareObject.setApplication(createApplication());
    compareObject.setKey(ENVIRONMENT_KEY);
    compareObject.setDescription(ENVIRONMENT_DESCRIPTION);

    assertNotEquals(compareObject, environment);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertNotEquals(compareObject, environment);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    assertEquals(environment.hashCode(), environment.hashCode());
  }

  @Test
  void twoObjectWithTheSameIdShouldHaveSameHashCode() {
    Environment compareObject = new Environment();
    compareObject.setId(ENVIRONMENT_ID);

    assertEquals(environment.hashCode(), compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    Environment compareObject = new Environment();
    compareObject.setId(12L);

    assertNotEquals(environment.hashCode(), compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    String objectToString = environment.toString();

    assertEquals("Environment("
        + "id=" + ENVIRONMENT_ID + ", "
        + "application=" + environment.getApplication().toString() + ", "
        + "key=" + ENVIRONMENT_KEY + ", "
        + "description=" + ENVIRONMENT_DESCRIPTION
        + ")", objectToString);
  }

  @Test
  void shouldValidate() {
    Set<ConstraintViolation<Environment>> violations
        = validator.validate(environment);

    assertTrue(violations.isEmpty());
  }

  @Test
  void shouldNotValidateKeyWithLengthMoreThan32() {
    final String longKey = "lorem-ipsum-dolor-sit-amet-consectetur-adipiscing-elit-praesent-quam";

    environment.setKey(longKey);

    Set<ConstraintViolation<Environment>> violations
        = validator.validate(environment);

    assertEquals(1, violations.size());
    ConstraintViolation<Environment> violation
        = violations.iterator().next();
    assertEquals("key", violation.getPropertyPath().toString());
    assertEquals(longKey, violation.getInvalidValue());
  }

  @Test
  void shouldNotValidateBlankKey() {
    environment.setKey("   ");

    Set<ConstraintViolation<Environment>> violations
        = validator.validate(environment);

    assertEquals(1, violations.size());
    ConstraintViolation<Environment> violation
        = violations.iterator().next();
    assertEquals("key", violation.getPropertyPath().toString());
    assertEquals("   ", violation.getInvalidValue());
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
