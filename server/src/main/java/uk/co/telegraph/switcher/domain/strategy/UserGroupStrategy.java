package uk.co.telegraph.switcher.domain.strategy;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.ClientInfo;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupStrategy extends Strategy {

  @ManyToOne
  private UserGroup userGroup;

  @Override
  public boolean isEnabled(ClientInfo clientInfo) {
    return false;
  }
}
