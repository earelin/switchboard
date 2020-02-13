package uk.co.telegraph.switcher.domain.strategy;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uk.co.telegraph.switcher.domain.ClientInfo;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
public class UserGroupStrategy extends Strategy {

  private UserGroup userGroup;

  public UserGroupStrategy(long id) {
    super(id);
  }

  @Override
  public boolean isEnabled(ClientInfo clientInfo) {
    return userGroup.hasUser(clientInfo.getUser());
  }
}
