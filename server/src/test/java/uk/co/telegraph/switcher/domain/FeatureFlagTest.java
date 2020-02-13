package uk.co.telegraph.switcher.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FeatureFlagTest {

  private static final String KEY = "performance.chart";
  private static final String DESCRIPTION = "Performance chart feature";
  private static final Boolean ACTIVE = true;

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
    featureFlag.setKey(KEY);
    featureFlag.setDescription(DESCRIPTION);
    featureFlag.setActive(ACTIVE);

    validator = validatorFactory.getValidator();
  }

  @Test
  void shouldSetKey() {
    assertThat(featureFlag.getKey())
        .isEqualTo(KEY);
  }

  @Test
  void shouldSetDescription() {
    assertThat(featureFlag.getDescription())
        .isEqualTo(DESCRIPTION);
  }

  @Test
  void shouldSetEnabled() {
    assertThat(featureFlag.isActive())
        .isTrue();
  }

  @Test
  void shouldCanEqualSameClass() {
    FeatureFlag comparedObject = new FeatureFlag();

    assertThat(featureFlag.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "asdf";

    assertThat(featureFlag.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(featureFlag)
        .isEqualTo(featureFlag);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(featureFlag)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToAnEnvironmentWithSameId() {
    FeatureFlag compareObject = new FeatureFlag();
    compareObject.setKey(KEY);

    assertThat(featureFlag)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAnEnvironmentWithADifferentId() {
    FeatureFlag compareObject = new FeatureFlag();
    featureFlag.setKey(KEY);
    featureFlag.setDescription(DESCRIPTION);
    featureFlag.setActive(ACTIVE);

    assertThat(featureFlag)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(featureFlag)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    assertThat(featureFlag.hashCode())
        .isEqualTo(featureFlag.hashCode());
  }

  @Test
  void twoObjectWithTheSameIdShouldHaveSameHashCode() {
    FeatureFlag compareObject = new FeatureFlag();
    compareObject.setKey(KEY);

    assertThat(featureFlag.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    FeatureFlag compareObject = new FeatureFlag();
    compareObject.setKey("other-feature-flag");

    assertThat(featureFlag.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    String objectToString = featureFlag.toString();

    assertThat(featureFlag.toString())
        .isEqualTo("FeatureFlag("
            + "key=" + KEY + ", "
            + "description=" + DESCRIPTION + ", "
            + "active=" + ACTIVE + ", "
            + "strategySets="
            + ")");
  }
}
