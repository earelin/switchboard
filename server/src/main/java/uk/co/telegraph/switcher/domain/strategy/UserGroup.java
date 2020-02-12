package uk.co.telegraph.switcher.domain.strategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserGroup implements Serializable {
  private String key;

  @NotBlank
  private String name;

  private Set<String> users = new HashSet<>();

  public boolean hasUser(String user) {
    return users.contains(user);
  }
}
