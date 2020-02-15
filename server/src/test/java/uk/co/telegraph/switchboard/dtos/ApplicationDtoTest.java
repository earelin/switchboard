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
