package uk.co.telegraph.switcher.domain.strategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.ClientInfo;

@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultStrategy extends Strategy {
  private boolean value = false;

  @Override
  public boolean isEnabled(ClientInfo clientInfo) {
    return value;
  }
}
