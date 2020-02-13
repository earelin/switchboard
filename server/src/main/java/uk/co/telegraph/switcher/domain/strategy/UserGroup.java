package uk.co.telegraph.switcher.domain.strategy;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Group of users to be used in group related strategies.
 */
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserGroup {
  @EqualsAndHashCode.Include
  private final String key;

  private String name;

  private Set<String> users = new HashSet<>();

  public boolean hasUser(String user) {
    return users.contains(user);
  }
}
