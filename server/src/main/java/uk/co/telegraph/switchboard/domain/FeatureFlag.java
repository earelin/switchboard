package uk.co.telegraph.switchboard.domain;

import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import uk.co.telegraph.switchboard.domain.strategy.StrategySet;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Slf4j
public class FeatureFlag {
  @EqualsAndHashCode.Include
  private final String key;

  private String description;

  private boolean active = false;

  private Map<Context, StrategySet> strategySets;

  public boolean isFeatureEnabled(ClientInfo clientInfo) {
    Context context = clientInfo.getContext();
    if (strategySets.containsKey(context)) {
      StrategySet strategySet = strategySets.get(context);
      return strategySet.isFeatureEnabled(clientInfo);
    } else {
      log.warn("Not existing context in request: {}", clientInfo);
    }
    return false;
  }
}
