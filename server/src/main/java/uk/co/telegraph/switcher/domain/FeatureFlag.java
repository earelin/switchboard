package uk.co.telegraph.switcher.domain;

import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.strategy.StrategySet;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FeatureFlag {
  @EqualsAndHashCode.Include
  private String key;

  private String description;

  private boolean active = false;

  private Map<Context, StrategySet> strategySets;
}
