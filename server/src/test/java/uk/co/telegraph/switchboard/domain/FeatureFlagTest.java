package uk.co.telegraph.switchboard.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.strategy.StrategySet;

@ExtendWith(MockitoExtension.class)
class FeatureFlagTest {

  private static final String KEY = "performance.chart";
  private static final String DESCRIPTION = "Performance chart feature";
  private static final Boolean ACTIVE = true;

  @Mock
  private StrategySet strategySetProduction;

  @Mock
  private StrategySet strategySetTesting;

  private FeatureFlag featureFlag;

  @BeforeEach
  void setUp() {
    featureFlag = new FeatureFlag();
    featureFlag.setKey(KEY);
    featureFlag.setDescription(DESCRIPTION);
    featureFlag.setActive(ACTIVE);
    featureFlag.setStrategySets(generateStrategySets());
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
    assertThat(featureFlag.toString())
        .isEqualTo("FeatureFlag("
            + "key=" + KEY + ", "
            + "description=" + DESCRIPTION + ", "
            + "active=" + ACTIVE + ", "
            + "strategySets=" + generateStrategySets()
            + ")");
  }

  private Map<Context, StrategySet> generateStrategySets() {
    return Map.of(
        new Context("production"), strategySetProduction,
        new Context("testing"), strategySetTesting
    );
  }
}
