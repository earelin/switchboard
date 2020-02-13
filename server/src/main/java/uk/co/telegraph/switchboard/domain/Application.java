package uk.co.telegraph.switchboard.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Application {
  public Application(String key) {
    this.key = key;
  }

  @EqualsAndHashCode.Include
  @Size(max = 128)
  private final String key;

  @NotBlank @Size(max = 128)
  private String name;
  private String secret;
  private String description;
}
