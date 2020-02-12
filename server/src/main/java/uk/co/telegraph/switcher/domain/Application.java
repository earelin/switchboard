package uk.co.telegraph.switcher.domain;

import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application {
  @EqualsAndHashCode.Include
  private String key;

  private String name;
  private String secret;
  private String description;
  private Set<Environment> environments;
}
