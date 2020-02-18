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

package uk.co.telegraph.switchboard.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class KeyGeneratorServiceImplTest {

  private static final String NAME = "Newsroom Authoring";

  private KeyGeneratorService keyGeneratorService;

  @BeforeEach
  void setUp() {
    keyGeneratorService = new KeyGeneratorServiceImpl();
  }

  @Test
  void shouldGenerateAKey() {
    assertThat(keyGeneratorService.generateKey(NAME))
        .isNotBlank();
  }

  @Test
  void shouldGenerateDifferentKeysForTheSameName() {
    String key1 = keyGeneratorService.generateKey(NAME);
    String key2 = keyGeneratorService.generateKey(NAME);
    String key3 = keyGeneratorService.generateKey(NAME);

    assertThat(key1)
        .isNotEqualTo(key2);
    assertThat(key2)
        .isNotEqualTo(key3);
    assertThat(key1)
        .isNotEqualTo(key3);
  }

  @Test
  void shouldGenerateDifferentKeysForDifferentNames() {
    String key1 = keyGeneratorService.generateKey("Mobile Application");
    String key2 = keyGeneratorService.generateKey("Web Application");
    String key3 = keyGeneratorService.generateKey("System Service");

    assertThat(key1)
        .isNotEqualTo(key2);
    assertThat(key2)
        .isNotEqualTo(key3);
    assertThat(key1)
        .isNotEqualTo(key3);
  }

}
