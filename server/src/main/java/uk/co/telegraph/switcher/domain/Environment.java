package uk.co.telegraph.switcher.domain;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Environment {
  public static final String DEFAULT_ENVIRONMENT_KEY = "default";

  public static Environment buildDefault() {
    return new Environment(DEFAULT_ENVIRONMENT_KEY);
  }

  @EqualsAndHashCode.Include
  @NotBlank @Size(max = 64)
  private final String key;

  public Environment(String key) {
    this.key = key;
  }

  public boolean isDefault() {
    return Objects.equals(key, DEFAULT_ENVIRONMENT_KEY);
  }
}
