package uk.co.telegraph.switchboard.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ApplicationDtoTest {

  private static final String KEY = "newsroom-dashboard";
  private static final String NAME = "Newsroom Dashboard";
  private static final String SECRET = "e3b0c44298fc1c149afbf4c8996fb92427";
  private static final String DESCRIPTION = "An amazing application to be feature flagged";
  private static final Set<String> ENVIRONMENTS = Set.of("preprod", "staging", "prod");

  private ApplicationDto applicationDto;

  @BeforeEach
  void setUp() {
    applicationDto = new ApplicationDto();
    applicationDto.setKey(KEY);
    applicationDto.setName(NAME);
    applicationDto.setSecret(SECRET);
    applicationDto.setDescription(DESCRIPTION);
    applicationDto.setContexts(ENVIRONMENTS);
  }

  @Test
  void shouldSetKey() {
    assertThat(applicationDto.getKey())
        .isEqualTo(KEY);
  }

  @Test
  void shouldSetName() {
    assertThat(applicationDto.getName())
        .isEqualTo(NAME);
  }

  @Test
  void shouldSetSecret() {
    assertThat(applicationDto.getSecret())
        .isEqualTo(SECRET);
  }

  @Test
  void shouldSetDescription() {
    assertThat(applicationDto.getDescription())
        .isEqualTo(DESCRIPTION);
  }

  @Test
  void shouldSetEnvironments() {
    assertThat(applicationDto.getContexts())
        .isEqualTo(ENVIRONMENTS);
  }

}
