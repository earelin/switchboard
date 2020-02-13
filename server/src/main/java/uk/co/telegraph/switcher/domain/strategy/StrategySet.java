package uk.co.telegraph.switcher.domain.strategy;

import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import uk.co.telegraph.switcher.domain.ClientInfo;
import uk.co.telegraph.switcher.domain.Environment;

@Getter
@Setter
public class StrategySet {
  private Environment environment;

  private StrategyAggregator aggregator = StrategyAggregator.OR;

  private Set<Strategy> strategies;

  public boolean isEnabled(ClientInfo clientInfo) {
    
  }
}
