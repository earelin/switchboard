/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.telegraph.switchboard.dtos;

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
