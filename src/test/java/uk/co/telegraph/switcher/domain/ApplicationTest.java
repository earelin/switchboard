package uk.co.telegraph.switcher.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationTest {

  private Application application;

  @BeforeEach
  void setUp() {
    application = new Application();
  }

  @Test
  void shouldSetId() {
    application.setId("newsroom-dashboard");

    assertEquals("newsroom-dashboard", application.getId());
  }

  @Test
  void shouldSetName() {
    application.setName("Newsroom Dashboard");

    assertEquals("Newsroom Dashboard", application.getName());
  }

  @Test
  void shouldSetKey() {
    application.setKey("K2I1JPxYp1pCWprzf4QaReiwntZXxmu4");

    assertEquals("K2I1JPxYp1pCWprzf4QaReiwntZXxmu4", application.getKey());
  }

  @Test
  void shouldSetEnvironments() {
    Set<String> environments = Stream.of("dev", "stage", "prod")
        .collect(Collectors.toSet());

    application.setEnvironments(environments);

    assertEquals(environments, application.getEnvironments());
  }
}
