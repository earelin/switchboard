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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.jdbc.JdbcTestUtils;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.Context;

@SpringBootTest
@Transactional
@Tag("integration")
class ApplicationRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ApplicationRepository applicationRepository;

  @BeforeEach
  void setUp() {
    applicationRepository.deleteAll();
    applicationRepository.saveAll(ApplicationContentGenerator.generateApplications());
  }

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
    Application application = applicationRepository
        .findByKey("mobile-application")
        .orElseThrow();

    assertThat(application)
        .hasFieldOrPropertyWithValue("key", "mobile-application")
        .hasFieldOrPropertyWithValue("name", "Mobile Application")
        .hasFieldOrPropertyWithValue(
            "secret", "EU8ig9DWP0O41IZO420QwumGXC1sqJYE5lwvpGzUpuMoC9oj3tDeN63qBcZCBY3h")
        .hasFieldOrPropertyWithValue(
            "description", "Nullam ipsum orci, suscipit a nibh vel, tempus iaculis nibh.");

    assertThat(application.getContexts())
        .extracting("key")
        .contains("default");
  }

  @Test
  void findShouldReturnEmptyIfAnApplicationDoesExists() {
    assertThat(applicationRepository.findByKey("not-existing-application"))
        .isNotPresent();
  }

  @Test
  void shouldDeleteAnApplicationIfExists() {
    applicationRepository.deleteByKey("payment-application");

    assertThat(applicationRepository.count())
        .isEqualTo(4);
    assertThat(applicationRepository.findByKey("payment-application"))
        .isNotPresent();
  }

  @Test
  void shouldNotDeleteAnApplicationIfDoesNotExists() {
    applicationRepository.deleteByKey("not-existing-application");

    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "application"))
        .isEqualTo(5);
  }

  @Test
  void shouldFindAllApplications() {
    assertThat(applicationRepository.findAllByOrderByNameAsc())
        .isInstanceOf(List.class)
        .hasSize(5)
        .doesNotHaveDuplicates();
  }

  @Test
  void shouldCreateAnApplication() {
    Application application = new Application();
    application.setKey("new-application");
    application.setName("New application");
    application.setSecret("new secret");
    application.setDescription("Description");
    application.setContexts(Set.of(Context.buildDefault()));

    Application createdApplication = applicationRepository.save(application);

    assertThat(createdApplication)
        .hasFieldOrPropertyWithValue("key", "new-application")
        .hasFieldOrPropertyWithValue("name", "New application")
        .hasFieldOrPropertyWithValue("secret", "new secret")
        .hasFieldOrPropertyWithValue("description", "Description");

    assertThat(createdApplication.getContexts())
        .extracting("key")
        .contains("default");
  }

  @Test
  void shouldUpdateApplicationData() {
    Application application = applicationRepository
        .findByKey("mobile-application")
        .orElseThrow();
    application.setKey("mobile-application-updated");
    application.setName("Mobile Application updated");
    application.setSecret("Updated secret");
    application.setDescription("Updated description");
    application.setContexts(new HashSet<>(Set.of(
      new Context("testing")
    )));

    Application updatedApplication = applicationRepository.save(application);

    assertThat(updatedApplication)
        .hasFieldOrPropertyWithValue("key", "mobile-application-updated")
        .hasFieldOrPropertyWithValue("name", "Mobile Application updated")
        .hasFieldOrPropertyWithValue("secret", "Updated secret")
        .hasFieldOrPropertyWithValue("description", "Updated description");

    assertThat(updatedApplication.getContexts())
        .hasSize(1)
        .extracting("key")
        .contains("testing");
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
