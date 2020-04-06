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

package uk.co.telegraph.switchboard.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PasswordGeneratorImplTest {

  private PasswordGeneratorImpl passwordGenerator;

  @BeforeEach
  void setUp() {
    passwordGenerator = new PasswordGeneratorImpl();
  }

  @Test
  void should_generate_a_password_with_the_desired_size() {
    assertThat(passwordGenerator.generatePassword(32)).isNotBlank().hasSize(32);
  }

  @Test
  void should_generate_different_passwords_per_call() {
    Set<String> passwords =
        IntStream.of(16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16)
            .mapToObj(size -> passwordGenerator.generatePassword(size))
            .collect(Collectors.toSet());

    assertThat(passwords).hasSize(12).doesNotContainNull();
  }
}
