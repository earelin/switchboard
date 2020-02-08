package uk.co.telegraph.switcher.domain;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.strategy.Strategy;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FeatureFlag {
  @Id
  @EqualsAndHashCode.Include
  private String id;
  private String description;
  private Boolean enabled;

}
