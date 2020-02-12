package uk.co.telegraph.switcher.domain.strategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.ClientInfo;

@Data
public abstract class Strategy {
  @EqualsAndHashCode.Include
  private Long id;

  private StrategySet strategySet;

  public abstract boolean isEnabled(ClientInfo clientInfo);
}
