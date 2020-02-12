package uk.co.telegraph.switcher.domain.strategy;

import java.util.Set;
import lombok.Data;
import uk.co.telegraph.switcher.domain.Environment;

@Data
public class StrategySet {
  private Environment environment;

  private StrategyAggregator aggregator = StrategyAggregator.OR;

  private Set<Strategy> strategies;
}
