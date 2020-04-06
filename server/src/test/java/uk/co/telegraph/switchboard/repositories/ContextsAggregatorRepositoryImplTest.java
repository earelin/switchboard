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
import static org.mockito.Mockito.when;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_ID;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplication;
import static uk.co.telegraph.switchboard.generators.ContextsAggregatorContentGenerator.getContextsAggregator;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.ContextsAggregator;

@ExtendWith(MockitoExtension.class)
class ContextsAggregatorRepositoryImplTest {

  private Application application;

  @Mock
  private ContextsAggregatorJpaRepository contextsAggregatorJpaRepository;

  @InjectMocks
  private ContextsAggregatorRepositoryImpl contextsAggregatorRepository;

  @BeforeEach
  void setUp() {
    application = getApplication();
  }

  @Test
  void should_return_a_context_aggregator_by_application_if_exists() {
    ContextsAggregator contextsAggregator = getContextsAggregator(application);
    when(contextsAggregatorJpaRepository.findByApplication(application))
        .thenReturn(Optional.of(contextsAggregator));

    Optional<ContextsAggregator> contextsAggregatorOptional
        = contextsAggregatorRepository.getByApplication(application);

    assertThat(contextsAggregatorOptional)
        .isPresent()
        .get()
        .isEqualTo(contextsAggregator);
  }

  @Test
  void should_return_a_optional_empty_by_application_if_it_does_not_exists() {
    when(contextsAggregatorJpaRepository.findByApplication(application))
        .thenReturn(Optional.empty());

    Optional<ContextsAggregator> contextsAggregatorOptional
        = contextsAggregatorRepository.getByApplication(application);

    assertThat(contextsAggregatorOptional)
        .isNotPresent();
  }


  @Test
  void should_return_a_not_empty_optional_if_context_aggregator_find_by_id_exists() {
    ContextsAggregator contextsAggregator = getContextsAggregator(application);
    when(contextsAggregatorJpaRepository.findById(application.getId()))
        .thenReturn(Optional.of(contextsAggregator));

    Optional<ContextsAggregator> contextsAggregatorOptional
        = contextsAggregatorRepository.getByApplicationId(application.getId());

    assertThat(contextsAggregatorOptional)
        .isPresent()
        .get()
        .isEqualTo(contextsAggregator);
  }

  @Test
  void should_return_a_empty_optional_if_context_aggregator_find_by_id_does_not_exists() {
    when(contextsAggregatorJpaRepository.findById(application.getId()))
        .thenReturn(Optional.empty());

    Optional<ContextsAggregator> contextsAggregatorOptional
        = contextsAggregatorRepository.getByApplicationId(application.getId());

    assertThat(contextsAggregatorOptional)
        .isNotPresent();
  }

  @Test
  void should_return_true_if_context_aggregator_exists_by_application() {
    when(contextsAggregatorJpaRepository.existsByApplication(application))
        .thenReturn(true);

    assertThat(contextsAggregatorRepository.existsByApplication(application))
        .isTrue();
  }

  @Test
  void should_return_true_if_context_aggregator_does_not_exists_by_application() {
    when(contextsAggregatorJpaRepository.existsByApplication(application))
        .thenReturn(false);

    assertThat(contextsAggregatorRepository.existsByApplication(application))
        .isFalse();
  }

  @Test
  void should_return_true_if_context_aggregator_exists_by_application_id() {
    when(contextsAggregatorJpaRepository.existsById(APPLICATION_ID))
        .thenReturn(true);

    assertThat(contextsAggregatorRepository.existsByApplicationId(APPLICATION_ID))
        .isTrue();
  }

  @Test
  void should_return_true_if_context_aggregator_does_not_exists_by_application_id() {
    when(contextsAggregatorJpaRepository.existsById(APPLICATION_ID))
        .thenReturn(false);

    assertThat(contextsAggregatorRepository.existsByApplicationId(APPLICATION_ID))
        .isFalse();
  }

  @Test
  void should_save_a_context_aggregator() {
    ContextsAggregator contextsAggregator = getContextsAggregator(application);
    when(contextsAggregatorJpaRepository.save(contextsAggregator))
        .thenReturn(contextsAggregator);

    assertThat(contextsAggregatorRepository.save(contextsAggregator))
        .isEqualTo(contextsAggregator);
  }
}
