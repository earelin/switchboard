package uk.co.telegraph.switcher.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application implements Serializable {
  @Id @GeneratedValue
  @EqualsAndHashCode.Include
  private Long id;

  @Size(max = 128) @NotBlank
  private String key;

  @Size(max = 128) @NotBlank
  private String name;

  private String secret;

  @Column(columnDefinition = "TEXT")
  private String description;
}
