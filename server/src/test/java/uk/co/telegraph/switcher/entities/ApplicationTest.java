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

class ApplicationTest {

  private static final Long APPLICATION_ID = 25L;
  private static final String APPLICATION_KEY = "newsroom-dashboard";
  private static final String APPLICATION_NAME = "Newsroom Dashboard";
  private static final String APPLICATION_SECRET = "K2I1JPxYp1pCWprzf4QaReiwntZXxmu4";
  private static final String APPLICATION_DESCRIPTION
      = "An amazing application to be feature flagged";

  private static ValidatorFactory validatorFactory;

  private Application application;
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
    application = new Application();
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setKey(APPLICATION_KEY);
    application.setSecret(APPLICATION_SECRET);
    application.setDescription(APPLICATION_DESCRIPTION);

    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldSetId() {
    assertEquals(APPLICATION_ID, application.getId());
  }

  @Test
  void shouldSetKey() {
   assertEquals(APPLICATION_KEY, application.getKey());
  }

  @Test
  void shouldSetName() {
    assertEquals(APPLICATION_NAME, application.getName());
  }

  @Test
  void shouldSetSecret() {
    assertEquals(APPLICATION_SECRET, application.getSecret());
  }

  @Test
  void shouldSetDescription() {
    assertEquals(APPLICATION_DESCRIPTION, application.getDescription());
  }

  @Test
  void shouldCanEqualSameClass() {
    Application comparedObject = new Application();

    assertTrue(application.canEqual(comparedObject));
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "asdf";

    assertFalse(application.canEqual(comparedObject));
  }

  @Test
  void shouldBeEqualToItself() {
    assertEquals(application, application);
  }

  @Test
  void shouldNotBeEqualToNull() {
   assertNotEquals(null, application);
  }

  @Test
  void shouldBeEqualToAnApplicationWithSameId() {
    Application compareObject = new Application();
    compareObject.setId(APPLICATION_ID);

    assertEquals(compareObject, application);
  }

  @Test
  void shouldNotBeEqualToAnApplicationWithADifferentId() {
    Application compareObject = new Application();
    compareObject.setId(12L);
    compareObject.setName(APPLICATION_NAME);
    compareObject.setKey(APPLICATION_KEY);
    compareObject.setSecret(APPLICATION_SECRET);
    compareObject.setDescription(APPLICATION_DESCRIPTION);

    assertNotEquals(compareObject, application);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertNotEquals(compareObject, application);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    assertEquals(application.hashCode(), application.hashCode());
  }

  @Test
  void twoObjectWithTheSameIdShouldHaveSameHashCode() {
    Application compareObject = new Application();
    compareObject.setId(APPLICATION_ID);

    assertEquals(application.hashCode(), compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    Application compareObject = new Application();
    compareObject.setId(12L);
    compareObject.setName(APPLICATION_NAME);
    compareObject.setKey(APPLICATION_KEY);
    compareObject.setSecret(APPLICATION_SECRET);
    compareObject.setDescription(APPLICATION_DESCRIPTION);

    assertNotEquals(application.hashCode(), compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    String objectToString = application.toString();

    assertEquals("Application("
        + "id=" + APPLICATION_ID + ", "
        + "key=" + APPLICATION_KEY + ", "
        + "name=" + APPLICATION_NAME + ", "
        + "secret=" + APPLICATION_SECRET + ", "
        + "description=" + APPLICATION_DESCRIPTION
        + ")", objectToString);
  }

  @Test
  void shouldValidate() {
   Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertTrue(violations.isEmpty());
  }

  @Test
  void blankNameShouldNotValidate() {
    application.setName("   ");

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertEquals(1, violations.size());
    ConstraintViolation<Application> violation
        = violations.iterator().next();
    assertEquals("name", violation.getPropertyPath().toString());
    assertEquals("   ", violation.getInvalidValue());
  }

  @Test
  void shouldNotValidateKeyBiggerThan128() {
    final String longKey = "lorem-ipsum-dolor-sit-amet-consectetur-adipiscing-elit-praesent-quam"
        + "-tellus-consectetur-nec-neque-vel-lobortis-elementum-ex-curabitur";

    application.setKey(longKey);

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertEquals(1, violations.size());
    ConstraintViolation<Application> violation
        = violations.iterator().next();
    assertEquals("key", violation.getPropertyPath().toString());
    assertEquals(longKey, violation.getInvalidValue());
  }

  @Test
  void shouldNotValidateNameBiggerThan128() {
    final String longName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent"
        + " quam tellus, consectetur nec neque vel, lobortis elementum ex curabitur";

    application.setName(longName);

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertEquals(1, violations.size());
    ConstraintViolation<Application> violation
        = violations.iterator().next();
    assertEquals("name", violation.getPropertyPath().toString());
    assertEquals(longName, violation.getInvalidValue());
  }
}
