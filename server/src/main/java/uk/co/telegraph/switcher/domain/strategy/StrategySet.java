package uk.co.telegraph.switcher.domain.strategy;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import uk.co.telegraph.switcher.domain.Environment;
import uk.co.telegraph.switcher.domain.FeatureFlag;

@Entity
@Data
public class StrategySet implements Serializable {
  @Id @ManyToOne
  private FeatureFlag featureFlag;
  @Id @ManyToOne
  private Environment environment;
  private StrategyAggregator aggregator;

  @OneToMany
  private Set<Strategy> strategies;
}
