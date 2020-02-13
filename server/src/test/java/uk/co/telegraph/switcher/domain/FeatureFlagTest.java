package uk.co.telegraph.switcher.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeatureFlagTest {

  private static final String FEATURE_FLAG_KEY = "performance.chart";
  private static final String FEATURE_FLAG_DESCRIPTION = "Performance chart feature";
  private static final Boolean FEATURE_FLAG_ACTIVE = true;

  private static ValidatorFactory validatorFactory;

  private FeatureFlag featureFlag;
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
    featureFlag = new FeatureFlag();
    featureFlag.setKey(FEATURE_FLAG_KEY);
    featureFlag.setDescription(FEATURE_FLAG_DESCRIPTION);
    featureFlag.setActive(FEATURE_FLAG_ACTIVE);

    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldSetKey() {
    assertEquals(FEATURE_FLAG_KEY, featureFlag.getKey());
  }

  @Test
  void shouldSetDescription() {
    assertEquals(FEATURE_FLAG_DESCRIPTION, featureFlag.getDescription());
  }

  @Test
  void shouldSetEnabled() {
    assertEquals(FEATURE_FLAG_ACTIVE, featureFlag.isActive());
  }

  @Test
  void shouldCanEqualSameClass() {
    FeatureFlag comparedObject = new FeatureFlag();

    assertTrue(featureFlag.canEqual(comparedObject));
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "asdf";

    assertFalse(featureFlag.canEqual(comparedObject));
  }

  @Test
  void shouldBeEqualToItself() {
    assertEquals(featureFlag, featureFlag);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertNotEquals(null, featureFlag);
  }

  @Test
  void shouldBeEqualToAnEnvironmentWithSameId() {
    FeatureFlag compareObject = new FeatureFlag();
    compareObject.setKey(FEATURE_FLAG_KEY);

    assertEquals(compareObject, featureFlag);
  }

  @Test
  void shouldNotBeEqualToAnEnvironmentWithADifferentId() {
    FeatureFlag compareObject = new FeatureFlag();
    featureFlag.setKey(FEATURE_FLAG_KEY);
    featureFlag.setDescription(FEATURE_FLAG_DESCRIPTION);
    featureFlag.setActive(FEATURE_FLAG_ACTIVE);

    assertNotEquals(compareObject, featureFlag);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertNotEquals(compareObject, featureFlag);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    assertEquals(featureFlag.hashCode(), featureFlag.hashCode());
  }

  @Test
  void twoObjectWithTheSameIdShouldHaveSameHashCode() {
    FeatureFlag compareObject = new FeatureFlag();
    compareObject.setKey(FEATURE_FLAG_KEY);

    assertEquals(featureFlag.hashCode(), compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    FeatureFlag compareObject = new FeatureFlag();
    compareObject.setKey("other-feature-flag");

    assertNotEquals(featureFlag.hashCode(), compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    String objectToString = featureFlag.toString();

    assertEquals("FeatureFlag("
        + "key=" + FEATURE_FLAG_KEY + ", "
        + "description=" + FEATURE_FLAG_DESCRIPTION + ", "
        + "active=" + FEATURE_FLAG_ACTIVE + ", "
        + "strategySets="
        + ")", objectToString);
  }
}
