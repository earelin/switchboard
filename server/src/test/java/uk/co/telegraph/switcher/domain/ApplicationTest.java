package uk.co.telegraph.switcher.domain;

import static org.assertj.core.api.Assertions.assertThat;

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

  private static final String KEY = "newsroom-dashboard";
  private static final String NAME = "Newsroom Dashboard";
  private static final String SECRET = "K2I1JPxYp1pCWprzf4QaReiwntZXxmu4";
  private static final String DESCRIPTION
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
    application = new Application(KEY);
    application.setName(NAME);
    application.setSecret(SECRET);
    application.setDescription(DESCRIPTION);

    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldSetKeyInConstruction() {
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
  void shouldCanEqualSameClass() {
    Application comparedObject = new Application("other-application");

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
  void shouldBeEqualToAnApplicationWithSameKey() {
    Application compareObject = new Application(KEY);

    assertThat(application)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAnApplicationWithADifferentKey() {
    Application compareObject = new Application("other-application");
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
  void twoObjectsWithTheSameKeyShouldHaveSameHashCode() {
    Application compareObject = new Application(KEY);

    assertThat(application.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectsWithDifferentIdShouldHaveDifferentHashCode() {
    Application compareObject = new Application("other-application");
    compareObject.setName(NAME);
    compareObject.setSecret(SECRET);
    compareObject.setDescription(DESCRIPTION);

    assertThat(application.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(application.toString())
        .isEqualTo("Application("
            + "key=" + KEY + ", "
            + "name=" + NAME + ", "
            + "secret=" + SECRET + ", "
            + "description=" + DESCRIPTION
            + ")");
  }

  @Test
  void shouldValidate() {
   Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertThat(violations)
        .isEmpty();
  }

  @Test
  void shouldNotValidateKeyBiggerThan128() {
    final String longKey = "lorem-ipsum-dolor-sit-amet-consectetur-adipiscing-elit-praesent-quam"
        + "-tellus-consectetur-nec-neque-vel-lobortis-elementum-ex-curabitur";

    application = new Application(longKey);
    application.setName(NAME);
    application.setSecret(SECRET);
    application.setDescription(DESCRIPTION);

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertThat(violations)
        .hasSize(1);

    ConstraintViolation<Application> violation
        = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString())
        .isEqualTo("key");
    assertThat(violation.getInvalidValue())
        .isEqualTo(longKey);
  }

  @Test
  void blankNameShouldNotValidate() {
    application.setName("   ");

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertThat(violations)
        .hasSize(1);

    ConstraintViolation<Application> violation
        = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString())
        .isEqualTo("name");
    assertThat(violation.getInvalidValue())
        .isEqualTo("   ");
  }

  @Test
  void shouldNotValidateNameBiggerThan128() {
    final String longName = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent"
        + " quam tellus, consectetur nec neque vel, lobortis elementum ex curabitur";

    application.setName(longName);

    Set<ConstraintViolation<Application>> violations
        = validator.validate(application);

    assertThat(violations)
        .hasSize(1);

    ConstraintViolation<Application> violation
        = violations.iterator().next();
    assertThat(violation.getPropertyPath().toString())
        .isEqualTo("name");
    assertThat(violation.getInvalidValue())
        .isEqualTo(longName);
  }

  private Set<Context> generateEnvironments() {
    return Set.of(
        new Context("development"),
        new Context("staging"),
        new Context("production")
    );
  }
}
