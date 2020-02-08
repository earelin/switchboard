package uk.co.telegraph.switcher.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application {
  @Id
  @EqualsAndHashCode.Include
  private String id;
  private String name;
  private String key;
  @ElementCollection
  private Set<String> environments = new HashSet<>();
}
