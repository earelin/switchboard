package uk.co.telegraph.switcher.domain.strategy;

import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import uk.co.telegraph.switcher.domain.ClientInfo;

@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(
    callSuper = true,
    onlyExplicitlyIncluded = true
)
public class DefaultStrategy extends Strategy {

  private boolean enabled = false;

  public DefaultStrategy(long id) {
    super(id);
  }

  @Override
  public boolean isEnabled(ClientInfo clientInfo) {
    return enabled;
  }
}
