package uk.co.telegraph.switcher.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FeatureFlag {
  @EqualsAndHashCode.Include
  @Size(max = 128) @NotBlank
  private String key;

  private String description;

  @NotNull
  private boolean active = false;
}

