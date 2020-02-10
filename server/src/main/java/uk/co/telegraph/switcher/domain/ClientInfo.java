package uk.co.telegraph.switcher.domain;

import lombok.Data;
import uk.co.telegraph.switcher.entities.Application;
import uk.co.telegraph.switcher.entities.Environment;

@Data
public class ClientInfo {
  private Environment environment;
  private String instance;
  private String userId;

  public Application getApplication() {
    return environment != null ? environment.getApplication() : null;
  }
}
