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
