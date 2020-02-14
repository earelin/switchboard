package uk.co.telegraph.switchboard.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.Context;

@ExtendWith(MockitoExtension.class)
class StrategySetTest {

  private static final Context CONTEXT = new Context("production");

  private StrategySet strategySet;

  @Mock
  private Strategy strategy1;

  @Mock
  private Strategy strategy2;

  @Mock
  private Strategy strategy3;

  @BeforeEach
  void setUp() {
    strategySet = new StrategySet(CONTEXT);
    strategySet.setAggregator(StrategyAggregator.AND);
    strategySet.setStrategies(generateStrategies());
  }

  @Test
  void shouldSetContextOnConstructor() {
    assertThat(strategySet.getContext())
        .isEqualTo(CONTEXT);
  }

  @Test
  void shouldSetAggregator() {
    assertThat(strategySet.getAggregator())
        .isEqualTo(StrategyAggregator.AND);
  }

  @Test
  void shouldSetStrategies() {
    assertThat(strategySet.getStrategies())
        .isEqualTo(generateStrategies());
  }

  @Test
  void shouldBeEnabledWithAndAggregatorAndAllEnabledStrategies() {
    when(strategy1.isFeatureEnabled(any())).thenReturn(true);
    when(strategy2.isFeatureEnabled(any())).thenReturn(true);
    when(strategy3.isFeatureEnabled(any())).thenReturn(true);

    assertThat(strategySet.isFeatureEnabled(null))
        .isTrue();
  }

  @Test
  void shouldNotBeEnabledWithAndAggregatorAndOneNotEnabledStrategy() {
    when(strategy1.isFeatureEnabled(any())).thenReturn(true);
    when(strategy2.isFeatureEnabled(any())).thenReturn(false);
    when(strategy3.isFeatureEnabled(any())).thenReturn(true);

    assertThat(strategySet.isFeatureEnabled(null))
        .isFalse();
  }

  @Test
  void shouldBeEnabledWithAndAggregatorOrAndOneEnabledStrategies() {
    strategySet.setAggregator(StrategyAggregator.OR);
    when(strategy1.isFeatureEnabled(any())).thenReturn(false);
    when(strategy2.isFeatureEnabled(any())).thenReturn(true);
    when(strategy3.isFeatureEnabled(any())).thenReturn(false);

    assertThat(strategySet.isFeatureEnabled(null))
        .isTrue();
  }

  @Test
  void shouldNotBeEnabledWithAndAggregatorOrAndAllStrategiesNotEnabled() {
    strategySet.setAggregator(StrategyAggregator.OR);
    when(strategy1.isFeatureEnabled(any())).thenReturn(false);
    when(strategy2.isFeatureEnabled(any())).thenReturn(false);
    when(strategy3.isFeatureEnabled(any())).thenReturn(false);

    assertThat(strategySet.isFeatureEnabled(null))
        .isFalse();
  }

  @Test
  void shouldNotBeEnabledWithEmptyStrategies() {
    strategySet.setStrategies(new HashSet<>());

    assertThat(strategySet.isFeatureEnabled(null))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    StrategySet comparedObject = new StrategySet(new Context("testing"));

    assertThat(strategySet.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "string";

    assertThat(strategySet.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(strategySet)
        .isEqualTo(strategySet);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(strategySet)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToADefaultStrategyWithSameId() {
    StrategySet compareObject = new StrategySet(new Context("production"));

    assertThat(strategySet)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADefaultStrategyWithADifferentId() {
    StrategySet compareObject = new StrategySet(new Context("testing"));
    compareObject.setAggregator(StrategyAggregator.AND);
    compareObject.setStrategies(generateStrategies());

    assertThat(strategySet)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(strategySet)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameInstanceShouldHaveSameHashCode() {
    assertThat(strategySet.hashCode())
        .isEqualTo(strategySet.hashCode());
  }

  @Test
  void twoApplicationsWithTheSameIdShouldHaveSameHashCode() {
    StrategySet compareObject = new StrategySet(new Context("production"));

    assertThat(strategySet.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    StrategySet compareObject = new StrategySet(new Context("testing"));
    compareObject.setAggregator(StrategyAggregator.AND);
    compareObject.setStrategies(generateStrategies());

    assertThat(strategySet.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(strategySet.toString())
        .isEqualTo("StrategySet("
            + "context=" + CONTEXT + ", "
            + "aggregator=AND, "
            + "strategies=" + generateStrategies()
            + ")");
  }

  private Set<Strategy> generateStrategies() {
    return Set.of(strategy1, strategy2, strategy3);
  }

}
