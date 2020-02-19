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
import static uk.co.telegraph.switchboard.Definitions.TEST_INTEGRATION_TAG;

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
import uk.co.telegraph.switchboard.utils.ApplicationContentGenerator;

@SpringBootTest
@Transactional
@Tag(TEST_INTEGRATION_TAG)
class ApplicationRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ApplicationRepository applicationRepository;

  @BeforeEach
  void setUp() {
    applicationRepository.deleteAll();
    applicationRepository.saveAll(ApplicationContentGenerator.generateApplicationList());
  }

  @Test
  void shouldCountRecords() {
    assertThat(applicationRepository.count())
        .isEqualTo(5);
  }

  @Test
  void existsShouldReturnTrueIfAnApplicationExists() {
    assertThat(applicationRepository.existsById("fd528ad1-6072-3d96-b5af-ff901ac93818"))
        .isTrue();
  }

  @Test
  void existsShouldReturnFalseIfAnApplicationDoesNotExists() {
    assertThat(applicationRepository.existsById("not-existing-application"))
        .isFalse();
  }

  @Test
  void findShouldReturnAnApplicationIfItDoesExists() {
    Application application = applicationRepository
        .findById("4e6aea3a-506c-3b7b-a3c3-74e442d1dfa4")
        .orElseThrow();

    assertThat(application)
        .hasFieldOrPropertyWithValue("id", "4e6aea3a-506c-3b7b-a3c3-74e442d1dfa4")
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
    assertThat(applicationRepository.findById("not-existing-application"))
        .isNotPresent();
  }

  @Test
  void shouldDeleteAnApplicationIfExists() {
    applicationRepository.deleteById("e6211ab1-8838-38ec-a10f-d36f673bb84f");

    assertThat(applicationRepository.count())
        .isEqualTo(4);
    assertThat(applicationRepository.findById("e6211ab1-8838-38ec-a10f-d36f673bb84f"))
        .isNotPresent();
  }

  @Test
  void shouldNotDeleteAnApplicationIfDoesNotExists() {
    applicationRepository.deleteById("not-existing-application");

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
    application.setId("d2270af7-0528-38c0-94d2-5515c0156e8a");
    application.setName("New application");
    application.setSecret("new secret");
    application.setDescription("Description");
    application.setContexts(Set.of(Context.buildDefault()));

    Application createdApplication = applicationRepository.save(application);

    assertThat(createdApplication)
        .hasFieldOrPropertyWithValue("id", "d2270af7-0528-38c0-94d2-5515c0156e8a")
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
        .findById("4e6aea3a-506c-3b7b-a3c3-74e442d1dfa4")
        .orElseThrow();
    application.setName("Mobile Application updated");
    application.setSecret("Updated secret");
    application.setDescription("Updated description");
    application.setContexts(new HashSet<>(Set.of(new Context("testing"))));

    Application updatedApplication = applicationRepository.save(application);

    assertThat(updatedApplication)
        .hasFieldOrPropertyWithValue("id", "4e6aea3a-506c-3b7b-a3c3-74e442d1dfa4")
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
        .extracting(Application::getId)
        .containsExactly(
            "fd528ad1-6072-3d96-b5af-ff901ac93818",
            "d1cbbea4-67a3-3b70-9ab5-81da0af46238"
        );
  }

  @Test
  void shouldReturnOnePage() {
    Page<Application> applicationPage
        = applicationRepository.findAll(PageRequest.of(2, 2));

    assertThat(applicationPage.getTotalElements())
        .isEqualTo(5);
    assertThat(applicationPage.getContent())
        .hasSize(1)
        .extracting(Application::getId)
        .containsExactly("e6211ab1-8838-38ec-a10f-d36f673bb84f");
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
        .extracting(Application::getId)
        .containsExactly(
            "d1cbbea4-67a3-3b70-9ab5-81da0af46238",
            "fd528ad1-6072-3d96-b5af-ff901ac93818"
        );
  }
}
