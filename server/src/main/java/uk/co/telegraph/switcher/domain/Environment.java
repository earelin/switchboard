package uk.co.telegraph.switcher.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Environment {
  public static final String DEFAULT_ENVIRONMENT_KEY = "default";

  @EqualsAndHashCode.Include
  private String key;

  public boolean isDefault() {
    return DEFAULT_ENVIRONMENT_KEY.equals(key);
  }
}
