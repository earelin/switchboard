package uk.co.telegraph.switcher.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FeatureFlag implements Serializable {
  @Id @ManyToOne
  private Application application;
  @Id @EqualsAndHashCode.Include
  private String key;
  @Column(columnDefinition = "TEXT")
  private String description;
  private Boolean enabled;
}
