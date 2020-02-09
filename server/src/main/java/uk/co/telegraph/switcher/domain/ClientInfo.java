package uk.co.telegraph.switcher.domain;

import lombok.Data;

@Data
public class ClientInfo {
  private Application application;
  private String instance;
  private String userId;
  private String environment;
}