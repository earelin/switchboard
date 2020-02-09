package uk.co.telegraph.switcher.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Environment implements Serializable {
  @Id @ManyToOne
  private Application application;
  @Id @Size(max = 128)
  private String key;
  @Column(columnDefinition = "TEXT")
  private String description;
}
