package uk.co.telegraph.switcher.domain;

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
  private final String applicationKey;
  private final String environmentKey;
  private final String instance;
  private final String user;
  private final ZonedDateTime dateTime;
}
