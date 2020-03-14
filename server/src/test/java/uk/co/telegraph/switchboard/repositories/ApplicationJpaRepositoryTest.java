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

package uk.co.telegraph.switchboard.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.telegraph.switchboard.utils.ApplicationContentGenerator.APPLICATION_DESCRIPTION;
import static uk.co.telegraph.switchboard.utils.ApplicationContentGenerator.APPLICATION_ID;
import static uk.co.telegraph.switchboard.utils.ApplicationContentGenerator.APPLICATION_NAME;
import static uk.co.telegraph.switchboard.utils.ApplicationContentGenerator.APPLICATION_SECRET;
import static uk.co.telegraph.switchboard.utils.ApplicationContentGenerator.getApplication;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.jdbc.Sql;
import uk.co.telegraph.switchboard.RepositoryIntegration;
import uk.co.telegraph.switchboard.domain.Application;

@RepositoryIntegration
class ApplicationJpaRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ApplicationJpaRepository applicationRepository;

  @Test
  void should_find_an_application_by_id() {
    this.entityManager.persist(getApplication());

    Optional<Application> foundApplication = applicationRepository.findById(APPLICATION_ID);

    assertThat(foundApplication)
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION);
  }

  @Test
  void should_not_find_an_application_if_it_does_not_exists() {
    Optional<Application> foundApplication = applicationRepository.findById(APPLICATION_ID);

    assertThat(foundApplication)
        .isNotPresent();
  }

  @Test
  void should_save_an_application() {
    applicationRepository.save(getApplication());

    Application foundApplication = entityManager.find(Application.class, APPLICATION_ID);
    assertThat(foundApplication)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION);
  }

  @Test
  void should_delete_an_application() {
    this.entityManager.persist(getApplication());

    applicationRepository.deleteById(APPLICATION_ID);

    assertThat(entityManager.find(Application.class, APPLICATION_ID))
      .isNull();
  }

  @Test
  void should_return_true_if_an_application_exists() {
    this.entityManager.persist(getApplication());

    assertThat(applicationRepository.existsById(APPLICATION_ID))
        .isTrue();
  }

  @Test
  void should_return_false_if_an_application_does_not_exists() {
    assertThat(applicationRepository.existsById(APPLICATION_ID))
        .isFalse();
  }

  @Test
  @Sql("/data/applications.sql")
  void should_return_a_page_of_applications() {
    Pageable pageable = PageRequest.of(1, 3);

    Page<Application> applications = applicationRepository.findAll(pageable);

    assertThat(applications).hasSize(3);
    assertThat(applications.getTotalPages()).isEqualTo(4);
    assertThat(applications.getNumber()).isEqualTo(1);
    assertThat(applications.getTotalElements()).isEqualTo(10);
  }

  @Test
  @Sql("/data/applications.sql")
  void should_return_a_sorted_application_page() {
    Sort sort = Sort.by(Direction.DESC, "name");
    Pageable pageable = PageRequest.of(1, 3, sort);

    Page<Application> applications = applicationRepository.findAll(pageable);

    assertThat(applications).hasSize(3);
    assertThat(applications.getTotalPages()).isEqualTo(4);
    assertThat(applications.getNumber()).isEqualTo(1);
    assertThat(applications.getTotalElements()).isEqualTo(10);
    assertThat(applications.getContent())
        .extracting(application -> application.getName())
        .containsExactly("Mobile Authoring", "Mobile", "Logging");
  }
}
