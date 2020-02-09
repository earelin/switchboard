package uk.co.telegraph.switcher.domain.strategy;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.ClientInfo;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class DefaultStrategy extends Strategy {

  private Boolean defaultValue;

  @Override
  public boolean isEnabled(ClientInfo clientInfo) {
    return defaultValue;
  }

}
