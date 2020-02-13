package uk.co.telegraph.switcher.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateApplicationDtoTest {

  private static final String NAME = "Newsroom Dashboard";
  private static final String DESCRIPTION = "An amazing application to be feature flagged";
  private static final Set<String> ENVIRONMENTS = Set.of("preprod", "staging", "prod");

  private CreateApplicationDto createApplicationDto;

  @BeforeEach
  void setUp() {
    createApplicationDto = new CreateApplicationDto();
    createApplicationDto.setName(NAME);
    createApplicationDto.setDescription(DESCRIPTION);
    createApplicationDto.setContexts(ENVIRONMENTS);
  }

  @Test
  void shouldSetName() {
    assertThat(createApplicationDto.getName())
        .isEqualTo(NAME);
  }

  @Test
  void shouldSetDescription() {
    assertThat(createApplicationDto.getDescription())
        .isEqualTo(DESCRIPTION);
  }

  @Test
  void shouldSetEnvironments() {
    assertThat(createApplicationDto.getContexts())
        .isEqualTo(ENVIRONMENTS);
  }

}
