/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at:
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.co.telegraph.switchboard.infrastructure.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IdGeneratorImplTest {

  private IdGeneratorImpl idGenerator;

  @BeforeEach
  void setUp() {
    idGenerator = new IdGeneratorImpl();
  }

  @Test
  void should_generate_random_ids() {
    List<String> names =
        List.of("One application", "Another application", "Feature", "One application");

    Set<String> ids =
        names.stream().map(name -> idGenerator.generateId(name)).collect(Collectors.toSet());

    assertThat(ids).hasSize(names.size()).doesNotContainNull();
  }
}
