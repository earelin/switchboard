package uk.co.telegraph.switchboard.domain.strategy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uk.co.telegraph.switchboard.domain.ClientInfo;

/**
 * Algorithm for calculating one feature flag status condition for a particular client.
 */
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class Strategy {

  @EqualsAndHashCode.Include
  private final long id;

  public Strategy(long id) {
    this.id = id;
  }

  public abstract boolean isEnabled(ClientInfo clientInfo);
}
