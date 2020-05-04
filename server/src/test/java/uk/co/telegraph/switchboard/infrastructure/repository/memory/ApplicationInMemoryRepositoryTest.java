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

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_ID;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.telegraph.switchboard.domain.model.Application;
import uk.co.telegraph.switchboard.infrastructure.repository.dao.mapping.ApplicationDaoMapper;

class ApplicationInMemoryRepositoryTest {

  private ApplicationDaoMapper applicationDaoMapper = Mappers.getMapper(ApplicationDaoMapper.class);
  private ApplicationInMemoryRepository applicationRepository;

  @BeforeEach
  void setUp() {
    applicationRepository = new ApplicationInMemoryRepository(applicationDaoMapper);
  }

  @Test
  void should_create_and_return_one_application() {
    Application application = getApplication();

    applicationRepository.save(application);

    assertThat(applicationRepository.getById(APPLICATION_ID))
        .isPresent()
        .get()
        .isEqualTo(application);
  }

  @Test
  void should_remove_one_application() {
    Application application = getApplication();
    applicationRepository.save(application);

    applicationRepository.removeById(application.getId());

    assertThat(applicationRepository.getById(APPLICATION_ID))
        .isNotPresent();
  }

  @Test
  void should_return_true_if_an_application_exists() {
    Application application = getApplication();
    applicationRepository.save(application);

    assertThat(applicationRepository.existsById(APPLICATION_ID))
        .isTrue();
  }

  @Test
  void should_return_false_if_an_application_does_not_exists() {
    assertThat(applicationRepository.existsById(APPLICATION_ID))
        .isFalse();
  }

  @Test
  @Disabled
  void should_return_applications_first_page() {

  }
}
