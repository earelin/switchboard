package uk.co.telegraph.switcher.domain.strategy;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import uk.co.telegraph.switcher.domain.Application;

@Entity
@Data
public class UserGroup implements Serializable {

  @Id @GeneratedValue
  private Long id;

  @ManyToOne
  @NotNull
  private Application application;

  @NotBlank
  private String name;

  @ElementCollection
  private Set<String> users = new HashSet<>();

  public boolean hasUser(String user) {
    return users.contains(user);
  }
}
