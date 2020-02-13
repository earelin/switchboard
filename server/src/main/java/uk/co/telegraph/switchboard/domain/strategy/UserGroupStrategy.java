package uk.co.telegraph.switchboard.domain.strategy;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.telegraph.switchboard.domain.ClientInfo;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class UserGroupStrategy extends Strategy {

  private UserGroup userGroup;

  public UserGroupStrategy(long id) {
    super(id);
  }

  @Override
  public boolean isEnabled(ClientInfo clientInfo) {
    return userGroup != null && userGroup.hasUser(clientInfo.getUser());
  }
}
