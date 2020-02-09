package uk.co.telegraph.switcher.domain.strategy;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import uk.co.telegraph.switcher.domain.Application;

@Entity
@Data
public class UserGroup {

  @Id
  private Long id;
  @ManyToOne
  private Application application;
  private String name;
  @ElementCollection
  private Set<String> users = new HashSet<>();

  public boolean hasUser(String user) {
    return users.contains(user);
  }
}
