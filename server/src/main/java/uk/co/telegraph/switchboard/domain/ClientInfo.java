package uk.co.telegraph.switchboard.domain;

import java.time.ZonedDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class ClientInfo {
  private final String application;
  private final String context;
  private final String instance;
  private final String user;
  private final ZonedDateTime dateTime;
}
