package uk.co.telegraph.switcher.domain.strategy;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import uk.co.telegraph.switcher.domain.FeatureFlag;

@Entity
@Data
public class StrategySet {
  @Id
  private Long id;
  @ManyToOne
  private FeatureFlag featureFlag;
  private String environment;
  private StrategyAggregator aggregator;

  @OneToMany
  private Set<Strategy> strategies;
}
