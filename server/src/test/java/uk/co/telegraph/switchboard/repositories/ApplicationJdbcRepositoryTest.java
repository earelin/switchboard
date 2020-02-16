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

package uk.co.telegraph.switchboard.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.jdbc.JdbcTestUtils;
import uk.co.telegraph.switchboard.domain.Application;

@SpringBootTest
@Sql(
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
    scripts = {"/sql/application-jdbc-repository-test-suite.sql"}
)
@Tag("integration")
class ApplicationJdbcRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ApplicationRepository applicationRepository;

  @Test
  void shouldCountRecords() {
    assertThat(applicationRepository.count())
        .isEqualTo(5);
  }

  @Test
  void existsShouldReturnTrueIfAnApplicationExists() {
    assertThat(applicationRepository.existsByKey("editorial-dashboard"))
        .isTrue();
  }

  @Test
  void existsShouldReturnFalseIfAnApplicationDoesNotExists() {
    assertThat(applicationRepository.existsByKey("not-existing-application"))
        .isFalse();
  }

  @Test
  void findShouldReturnAnApplicationIfItDoesExists() {
    assertThat(applicationRepository.findByKey("mobile-application"))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("key", "mobile-application")
        .hasFieldOrPropertyWithValue("name", "Mobile Application")
        .hasFieldOrPropertyWithValue("secret", "EU8ig9DWP0O41IZO420QwumGXC1sqJYE5lwvpGzUpuMoC9oj3tDeN63qBcZCBY3h")
        .hasFieldOrPropertyWithValue("description", "Nullam ipsum orci, suscipit a nibh vel, tempus iaculis nibh.");
  }

  @Test
  void findShouldReturnEmptyIfAnApplicationDoesExists() {
    assertThat(applicationRepository.findByKey("not-existing-application"))
        .isNotPresent();
  }

  @Test
  void shouldDeleteAnApplicationIfExists() {
    applicationRepository.deleteByKey("payment-application");

    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "application"))
        .isEqualTo(4);
    assertThat(JdbcTestUtils.countRowsInTableWhere(
            jdbcTemplate, "application", "key = 'payment-application'"))
        .isEqualTo(0);
  }

  @Test
  void shouldNotDeleteAnApplicationIfDoesNotExists() {
    applicationRepository.deleteByKey("not-existing-application");

    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "application"))
        .isEqualTo(5);
  }

  @Test
  void shouldFindAllApplications() {
    assertThat(applicationRepository.findAll())
        .isInstanceOf(List.class)
        .hasSize(5)
        .doesNotHaveDuplicates();
  }

  @Test
  void shouldFindAllApplicationsAndReturnEmptyIfTableIsEmpty() {
    JdbcTestUtils.deleteFromTables(jdbcTemplate, "application");
    assertThat(applicationRepository.findAll())
        .isInstanceOf(List.class)
        .hasSize(0);
  }

  @Test
  void shouldUpdateApplicationData() {
    Application application = new Application("mobile-authoring");
    application.setName("Mobile Authoring updated");
    application.setSecret("new secret");
    application.setDescription("Updated description");

    applicationRepository.update(application);

    assertThat(applicationRepository.findByKey(application.getKey()))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("key", "mobile-authoring")
        .hasFieldOrPropertyWithValue("name", "Mobile Authoring updated")
        .hasFieldOrPropertyWithValue("secret", "new secret")
        .hasFieldOrPropertyWithValue("description", "Updated description");
  }

  @Test
  void shouldUpdateApplicationKeyAndData() {
    Application application = new Application("mobile-authoring-updated");
    application.setName("Mobile Authoring updated");
    application.setSecret("new secret");
    application.setDescription("Updated description");

    applicationRepository.updateByKey(application, "mobile-authoring");

    assertThat(applicationRepository.findByKey("mobile-authoring-updated"))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("key", "mobile-authoring-updated")
        .hasFieldOrPropertyWithValue("name", "Mobile Authoring updated")
        .hasFieldOrPropertyWithValue("secret", "new secret")
        .hasFieldOrPropertyWithValue("description", "Updated description");
  }

  @Test
  void shouldCreateAnApplication() {
    Application application = new Application("new-application");
    application.setName("New application");
    application.setSecret("new secret");
    application.setDescription("Description");

    applicationRepository.create(application);

    assertThat(applicationRepository.findByKey("new-application"))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("key", "new-application")
        .hasFieldOrPropertyWithValue("name", "New application")
        .hasFieldOrPropertyWithValue("secret", "new secret")
        .hasFieldOrPropertyWithValue("description", "Description");
  }

  @Test
  void shouldReturnFirstPage() {
    Page<Application> applicationPage
        = applicationRepository.findAll(PageRequest.of(0, 2));

    assertThat(applicationPage.getTotalElements())
        .isEqualTo(5);
    assertThat(applicationPage.getContent())
        .hasSize(2)
        .extracting(Application::getKey)
        .containsExactly("editorial-dashboard", "authoring-frontend");
  }

  @Test
  void shouldReturnOnePage() {
    Page<Application> applicationPage
        = applicationRepository.findAll(PageRequest.of(2, 2));

    assertThat(applicationPage.getTotalElements())
        .isEqualTo(5);
    assertThat(applicationPage.getContent())
        .hasSize(1)
        .extracting(Application::getKey)
        .containsExactly("payment-application");
  }

  @Test
  void shouldReturnOnePageOrderByName() {
    Sort sorting = Sort.by(Direction.ASC, "name");
    Page<Application> applicationPage
        = applicationRepository.findAll(PageRequest.of(0, 2, sorting));

    assertThat(applicationPage.getTotalElements())
        .isEqualTo(5);
    assertThat(applicationPage.getContent())
        .hasSize(2)
        .extracting(Application::getKey)
        .containsExactly("authoring-frontend", "editorial-dashboard");
  }
}
