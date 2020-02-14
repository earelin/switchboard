package uk.co.telegraph.switchboard.domain;

import java.time.ZonedDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode
public class ClientInfo {
  private final String applicationKey;
  private final String contextKey;
  private final String instance;
  private final String user;
  private final ZonedDateTime dateTime;

  ClientInfo(String applicationKey, String contextKey, String instance, String user,
      ZonedDateTime dateTime) {
    this.applicationKey = applicationKey;
    this.contextKey = contextKey;
    this.instance = instance;
    this.user = user;
    this.dateTime = dateTime;
  }

  public Context getContext() {
    return new Context(contextKey);
  }

  public static ClientInfoBuilder builder() {
    return new ClientInfoBuilder();
  }

  public static class ClientInfoBuilder {

    private String applicationKey;
    private String contextKey = Context.DEFAULT_KEY;
    private String instance;
    private String user;
    private ZonedDateTime dateTime;

    ClientInfoBuilder() {
    }

    public ClientInfoBuilder applicationKey(String applicationKey) {
      this.applicationKey = applicationKey;
      return this;
    }

    public ClientInfoBuilder contextKey(String contextKey) {
      this.contextKey = contextKey;
      return this;
    }

    public ClientInfoBuilder instance(String instance) {
      this.instance = instance;
      return this;
    }

    public ClientInfoBuilder user(String user) {
      this.user = user;
      return this;
    }

    public ClientInfoBuilder dateTime(ZonedDateTime dateTime) {
      this.dateTime = dateTime;
      return this;
    }

    public ClientInfo build() {
      return new ClientInfo(applicationKey, contextKey, instance, user, dateTime);
    }
  }
}
