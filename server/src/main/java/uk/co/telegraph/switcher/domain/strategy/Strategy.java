package uk.co.telegraph.switcher.domain.strategy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import uk.co.telegraph.switcher.domain.ClientInfo;

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
