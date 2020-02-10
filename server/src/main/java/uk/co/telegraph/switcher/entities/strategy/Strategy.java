package uk.co.telegraph.switcher.entities.strategy;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import uk.co.telegraph.switcher.domain.ClientInfo;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Strategy implements Serializable {
  @Id @GeneratedValue
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne
  private StrategySet strategySet;

  public abstract boolean isEnabled(ClientInfo clientInfo);
}
