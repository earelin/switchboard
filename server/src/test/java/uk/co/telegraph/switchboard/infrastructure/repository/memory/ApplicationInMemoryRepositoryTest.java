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

package uk.co.telegraph.switchboard.infrastructure.repository.memory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ApplicationInMemoryRepositoryTest {

  private ApplicationInMemoryRepository applicationRepository;

  @BeforeEach
  void setUp() {
    applicationRepository = new ApplicationInMemoryRepository();
  }

  @Test
  @Disabled
  void should_create_and_return_one_application() {

  }

  @Test
  @Disabled
  void should_create_and_remove_one_application() {

  }

  @Test
  @Disabled
  void should_return_true_if_an_application_exists() {

  }

  @Test
  @Disabled
  void should_return_false_if_an_application_does_not_exists() {

  }

  @Test
  @Disabled
  void should_return_applications_first_page() {

  }
}
