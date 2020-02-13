package uk.co.telegraph.switcher.domain.strategy;

import static uk.co.telegraph.switcher.domain.strategy.StrategyAggregator.AND;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BinaryOperator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.ClientInfo;
import uk.co.telegraph.switcher.domain.Context;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class StrategySet {
  @EqualsAndHashCode.Include
  private final Context context;

  private StrategyAggregator aggregator = StrategyAggregator.OR;

  private Set<Strategy> strategies = new HashSet<>();

  public StrategySet(Context context) {
    this.context = context;
  }

  public boolean isEnabled(ClientInfo clientInfo) {
    return strategies.stream()
      .map(strategy -> strategy.isEnabled(clientInfo))
      .reduce(strategyAggregator(aggregator))
      .orElse(false);
  }

  private BinaryOperator<Boolean> strategyAggregator(StrategyAggregator strategyAggregator) {
    if (strategyAggregator == AND) {
      return Boolean::logicalAnd;
    }
    return Boolean::logicalOr;
  }
}
