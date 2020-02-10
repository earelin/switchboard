package uk.co.telegraph.switcher.entities.strategy;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.strategy.StrategyAggregator;
import uk.co.telegraph.switcher.entities.Environment;
import uk.co.telegraph.switcher.entities.FeatureFlag;

@Entity
@Data
public class StrategySet implements Serializable {
  @Id @GeneratedValue
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne
  @NotNull
  private FeatureFlag featureFlag;

  @ManyToOne
  @NotNull
  private Environment environment;

  private StrategyAggregator aggregator = StrategyAggregator.OR;

  @OneToMany
  private Set<Strategy> strategies;
}
