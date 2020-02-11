package uk.co.telegraph.switcher.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Environment implements Serializable {
  @Id @GeneratedValue
  @EqualsAndHashCode.Include
  private Long id;

  @ManyToOne @NotNull
  private Application application;

  @Size(max = 32) @NotBlank
  private String key;

  @Column(columnDefinition = "TEXT")
  private String description;
}
