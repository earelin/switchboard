package uk.co.telegraph.switchboard.dtos;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateApplicationDtoTest {

  private static final String NAME = "Newsroom Dashboard";
  private static final String DESCRIPTION = "An amazing application to be feature flagged";

  private UpdateApplicationDto updateApplicationDto;

  @BeforeEach
  void setUp() {
    updateApplicationDto = new UpdateApplicationDto();
    updateApplicationDto.setName(NAME);
    updateApplicationDto.setDescription(DESCRIPTION);
  }

  @Test
  void shouldSetName() {
    assertThat(updateApplicationDto.getName())
        .isEqualTo(NAME);
  }

  @Test
  void shouldSetDescription() {
    assertThat(updateApplicationDto.getDescription())
        .isEqualTo(DESCRIPTION);
  }

}
