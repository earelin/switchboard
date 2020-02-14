package uk.co.telegraph.switchboard.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultStrategyTest {

  private static final long ID = 25;

  private DefaultStrategy defaultStrategy;

  @BeforeEach
  void setUp() {
    defaultStrategy = new DefaultStrategy(ID);
    defaultStrategy.setEnabled(true);
  }

  @Test
  void constructorShouldSetId() {
    assertThat(defaultStrategy.getId())
        .isEqualTo(ID);
  }

  @Test
  void shouldBeEnabledIfEnabledIsTrue() {
    assertThat(defaultStrategy.isFeatureEnabled(null))
        .isTrue();
  }

  @Test
  void shouldNotBeEnabledIfEnabledIsFalse() {
    defaultStrategy.setEnabled(false);

    assertThat(defaultStrategy.isFeatureEnabled(null))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    DefaultStrategy comparedObject = new DefaultStrategy(12);

    assertThat(defaultStrategy.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "string";

    assertThat(defaultStrategy.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(defaultStrategy)
        .isEqualTo(defaultStrategy);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(defaultStrategy)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToADefaultStrategyWithSameId() {
    DefaultStrategy compareObject = new DefaultStrategy(ID);

    assertThat(defaultStrategy)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADefaultStrategyWithADifferentId() {
    DefaultStrategy compareObject = new DefaultStrategy(12);
    compareObject.setEnabled(true);

    assertThat(defaultStrategy)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(defaultStrategy)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameInstanceShouldHaveSameHashCode() {
    assertThat(defaultStrategy.hashCode())
        .isEqualTo(defaultStrategy.hashCode());
  }

  @Test
  void twoApplicationsWithTheSameIdShouldHaveSameHashCode() {
    DefaultStrategy compareObject = new DefaultStrategy(ID);

    assertThat(defaultStrategy.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    DefaultStrategy compareObject = new DefaultStrategy(12);
    compareObject.setEnabled(true);

    assertThat(defaultStrategy.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(defaultStrategy.toString())
        .isEqualTo("DefaultStrategy("
            + "super=Strategy(id=" + ID + "), "
            + "enabled=true)");
  }
}
