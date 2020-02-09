package uk.co.telegraph.switcher.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application implements Serializable {
  @Id
  @EqualsAndHashCode.Include
  @Size(max = 128)
  private String id;
  @NotBlank
  @Size(max = 128)
  private String name;
  private String secretKey;
  @Column(columnDefinition = "TEXT")
  private String description;
}
