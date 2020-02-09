package uk.co.telegraph.switcher.domain;

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

  private static final String APPLICATION_ID = "newsroom-dashboard";
  private static final String APPLICATION_NAME = "Newsroom Dashboard";
  private static final String APPLICATION_KEY = "K2I1JPxYp1pCWprzf4QaReiwntZXxmu4";
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
    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldSetId() {
    application.setId(APPLICATION_ID);

    assertEquals(APPLICATION_ID, application.getId());
  }

  @Test
  void shouldSetName() {
    application.setName(APPLICATION_NAME);

    assertEquals(APPLICATION_NAME, application.getName());
  }

  @Test
  void shouldsetSecretKey() {
    application.setSecretKey(APPLICATION_KEY);

    assertEquals(APPLICATION_KEY, application.getSecretKey());
  }

  @Test
  void shouldSetDescription() {
    application.setDescription(APPLICATION_DESCRIPTION);

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
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    assertEquals(application, application);
  }

  @Test
  void shouldNotBeEqualToNull() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    assertNotEquals(null, application);
  }

  @Test
  void shouldBeEqualToAnApplicationWithSameId() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    Application compareObject = new Application();
    compareObject.setId(APPLICATION_ID);

    assertEquals(compareObject, application);
  }

  @Test
  void shouldNotBeEqualToAnApplicationWithADifferentId() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    Application compareObject = new Application();
    compareObject.setId("another-application");
    compareObject.setName(APPLICATION_NAME);
    compareObject.setSecretKey(APPLICATION_KEY);
    compareObject.setDescription(APPLICATION_DESCRIPTION);

    assertNotEquals(compareObject, application);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    String compareObject = "testing";

    assertNotEquals(compareObject, application);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    assertEquals(application.hashCode(), application.hashCode());
  }

  @Test
  void twoObjectWithTheSameIdShouldHaveSameHashCode() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    Application compareObject = new Application();
    compareObject.setId(APPLICATION_ID);

    assertEquals(application.hashCode(), compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    Application compareObject = new Application();
    compareObject.setId("another-application");
    compareObject.setName(APPLICATION_NAME);
    compareObject.setSecretKey(APPLICATION_KEY);
    compareObject.setDescription(APPLICATION_DESCRIPTION);

    assertNotEquals(application.hashCode(), compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    String objectToString = application.toString();

    assertEquals("Application("
        + "id=newsroom-dashboard, "
        + "name=Newsroom Dashboard, "
        + "secretKey=K2I1JPxYp1pCWprzf4QaReiwntZXxmu4, "
        + "description=An amazing application to be feature flagged"
        + ")", objectToString);
  }

  @Test
  void shouldValidate() {
    application.setId(APPLICATION_ID);
    application.setName(APPLICATION_NAME);
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertTrue(violations.isEmpty());
  }

  @Test
  void blankNameShouldNotValidate() {
    application.setId(APPLICATION_ID);
    application.setName("   ");
    application.setSecretKey(APPLICATION_KEY);
    application.setDescription(APPLICATION_DESCRIPTION);

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertEquals(1, violations.size());
    ConstraintViolation<Application> violation
        = violations.iterator().next();
    assertEquals("name", violation.getPropertyPath().toString());
    assertEquals("   ", violation.getInvalidValue());
  }

  @Test
  void shouldNotValidateIdWithLengthMoreThan128() {
    final String longId = "lorem-ipsum-dolor-sit-amet-consectetur-adipiscing-elit-praesent-quam"
        + "-tellus-consectetur-nec-neque-vel-lobortis-elementum-ex-curabitur";

    application.setId(longId);
    application.setName(APPLICATION_NAME);

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertEquals(1, violations.size());
    ConstraintViolation<Application> violation
        = violations.iterator().next();
    assertEquals("id", violation.getPropertyPath().toString());
    assertEquals(longId, violation.getInvalidValue());
  }

  @Test
  void shouldNotValidateNameWithLengthMoreThan128() {
    final String longName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent"
        + " quam tellus, consectetur nec neque vel, lobortis elementum ex curabitur";

    application.setId(APPLICATION_ID);
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
