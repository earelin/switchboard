package uk.co.telegraph.switcher.domain.strategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.ClientInfo;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserGroupStrategy extends Strategy {

  private UserGroup userGroup;

  @Override
  public boolean isEnabled(ClientInfo clientInfo) {
    return userGroup.hasUser(clientInfo.getUser());
  }
}
