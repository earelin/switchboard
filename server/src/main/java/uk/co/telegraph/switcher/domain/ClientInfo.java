package uk.co.telegraph.switcher.domain;

import lombok.Data;

@Data
public class ClientInfo {
  private Application application;
  private Environment environment;
  private String instance;
  private String user;
}
