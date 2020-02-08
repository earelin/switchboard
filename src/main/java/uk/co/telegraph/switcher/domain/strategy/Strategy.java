package uk.co.telegraph.switcher.domain.strategy;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import lombok.Data;
import uk.co.telegraph.switcher.domain.ClientInfo;
import uk.co.telegraph.switcher.domain.FeatureFlag;

@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@Data
public abstract class Strategy {
  @Id
  private Long id;
  @ManyToOne
  private FeatureFlag featureFlag;

  public abstract boolean isEnabled(ClientInfo clientInfo);
}
