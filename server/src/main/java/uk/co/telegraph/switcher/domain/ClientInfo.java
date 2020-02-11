package uk.co.telegraph.switcher.domain;

import lombok.Data;

@Data
public class ClientInfo {
  private Environment environment;
  private String instance;
  private String user;

  public Application getApplication() {
    return environment != null ? environment.getApplication() : null;
  }
}
