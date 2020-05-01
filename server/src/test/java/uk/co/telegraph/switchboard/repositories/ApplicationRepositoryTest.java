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
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_DESCRIPTION;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_ID;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_NAME;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_SECRET;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplication;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplicationList;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.infrastructure.jpa.repositories.ApplicationJpaRepository;

@ExtendWith(MockitoExtension.class)
class ApplicationRepositoryTest {

  @Mock
  private ApplicationJpaRepository applicationJpaRepository;

  @InjectMocks
  private ApplicationRepository applicationRepository;

  @Test
  void should_return_a_found_application() {
    when(applicationJpaRepository.findById(APPLICATION_ID))
        .thenReturn(Optional.of(getApplication()));

    Optional<Application> application = applicationRepository.getById(APPLICATION_ID);

    assertThat(application)
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION);
  }

  @Test
  void should_return_empty_if_an_application_is_not_found() {
    when(applicationJpaRepository.findById(APPLICATION_ID)).thenReturn(Optional.empty());

    Optional<Application> application = applicationRepository.getById(APPLICATION_ID);

    assertThat(application).isNotPresent();
  }

  @Test
  void should_save_an_application() {
    Application application = getApplication();
    when(applicationJpaRepository.save(application)).thenAnswer(returnsFirstArg());

    Application savedApplication = applicationRepository.save(application);

    assertThat(savedApplication)
        .hasFieldOrPropertyWithValue("id", APPLICATION_ID)
        .hasFieldOrPropertyWithValue("name", APPLICATION_NAME)
        .hasFieldOrPropertyWithValue("secret", APPLICATION_SECRET)
        .hasFieldOrPropertyWithValue("description", APPLICATION_DESCRIPTION);
    verify(applicationJpaRepository).save(application);
  }

  @Test
  void should_remove_an_application() {
    applicationRepository.removeById(APPLICATION_ID);

    verify(applicationJpaRepository).deleteById(APPLICATION_ID);
  }

  @Test
  void should_return_true_if_application_exists() {
    when(applicationJpaRepository.existsById(APPLICATION_ID)).thenReturn(true);

    assertThat(applicationRepository.existsById(APPLICATION_ID)).isTrue();
  }

  @Test
  void should_return_false_if_application_does_not_exists() {
    when(applicationJpaRepository.existsById(APPLICATION_ID)).thenReturn(false);

    assertThat(applicationRepository.existsById(APPLICATION_ID)).isFalse();
  }

  @Test
  void should_call_a_paged_application_list() throws FileNotFoundException {
    List<Application> applications = getApplicationList();
    Pageable pageable = PageRequest.of(0, 10);
    when(applicationJpaRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(applications, pageable, 30));

    Page<Application> applicationPage = applicationRepository.getPagedList(pageable);

    assertThat(applicationPage)
        .hasSize(10)
        .containsExactly(applications.toArray(new Application[applications.size()]));
  }
}
