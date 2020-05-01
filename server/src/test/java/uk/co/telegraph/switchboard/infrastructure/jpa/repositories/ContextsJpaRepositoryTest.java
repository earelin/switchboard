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

package uk.co.telegraph.switchboard.infrastructure.jpa.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.APPLICATION_ID;
import static uk.co.telegraph.switchboard.generators.ApplicationContentGenerator.getApplication;
import static uk.co.telegraph.switchboard.generators.ContextsAggregatorContentGenerator.CONTEXT_PRODUCTION_NAME;
import static uk.co.telegraph.switchboard.generators.ContextsAggregatorContentGenerator.CONTEXT_STAGING_NAME;
import static uk.co.telegraph.switchboard.generators.ContextsAggregatorContentGenerator.getContextsAggregator;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import uk.co.telegraph.switchboard.RepositoryIntegration;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.ContextsAggregator;

@RepositoryIntegration
class ContextsJpaRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private ContextsJpaRepository contextsAggregatorRepository;

  @Test
  void should_find_a_contexts_aggregator_by_application() {
    Application application = getApplication();
    this.entityManager.persist(application);
    this.entityManager.persist(getContextsAggregator(application));

    Optional<ContextsAggregator> contextsAggregatorOptional =
        contextsAggregatorRepository.findByApplication(application);

    assertThat(contextsAggregatorOptional)
        .isPresent();
    ContextsAggregator contextsAggregator = contextsAggregatorOptional.get();
    assertThat(contextsAggregator.getApplication())
        .isEqualTo(application);
    assertThat(contextsAggregator.getContexts())
        .containsKeys(CONTEXT_PRODUCTION_NAME, CONTEXT_STAGING_NAME);
  }

  @Test
  void should_return_optional_if_a_contexts_aggregator_does_not_exists_by_application() {
    Optional<ContextsAggregator> contextsAggregatorOptional =
        contextsAggregatorRepository.findByApplication(getApplication());

    assertThat(contextsAggregatorOptional)
        .isNotPresent();
  }

  @Test
  void should_find_a_contexts_aggregator_by_application_id() {
    Application application = getApplication();
    this.entityManager.persist(application);
    this.entityManager.persist(getContextsAggregator(application));

    Optional<ContextsAggregator> contextsAggregatorOptional =
        contextsAggregatorRepository.findById(application.getId());

    assertThat(contextsAggregatorOptional)
        .isPresent();
    ContextsAggregator contextsAggregator = contextsAggregatorOptional.get();
    assertThat(contextsAggregator.getApplication())
        .isEqualTo(application);
    assertThat(contextsAggregator.getContexts())
        .containsKeys(CONTEXT_PRODUCTION_NAME, CONTEXT_STAGING_NAME);
  }

  @Test
  void should_return_optional_if_a_contexts_aggregator_does_not_exists_by_id() {
    Optional<ContextsAggregator> contextsAggregatorOptional =
        contextsAggregatorRepository.findById(APPLICATION_ID);

    assertThat(contextsAggregatorOptional)
        .isNotPresent();
  }

  @Test
  void should_save_a_context_aggregator() {
    Application application = getApplication();
    this.entityManager.persist(application);
    ContextsAggregator contextsAggregator = getContextsAggregator(application);

    contextsAggregatorRepository.save(contextsAggregator);

    ContextsAggregator foundContextsAggregator
        = entityManager.find(ContextsAggregator.class, application.getId());
    assertThat(foundContextsAggregator.getApplication())
        .isEqualTo(application);
    assertThat(foundContextsAggregator.getContexts())
        .containsKeys(CONTEXT_PRODUCTION_NAME, CONTEXT_STAGING_NAME);
  }

  @Test
  void should_true_if_a_contexts_aggregator_exists_by_application() {
    Application application = getApplication();
    this.entityManager.persist(application);
    this.entityManager.persist(getContextsAggregator(application));

    assertThat(contextsAggregatorRepository.existsByApplication(application))
        .isTrue();
  }

  @Test
  void should_false_if_a_contexts_aggregator_does_not_exists_by_application() {
    Application application = getApplication();

    assertThat(contextsAggregatorRepository.existsByApplication(application))
        .isFalse();
  }

  @Test
  void should_true_if_a_contexts_aggregator_exists_by_application_id() {
    Application application = getApplication();
    this.entityManager.persist(application);
    this.entityManager.persist(getContextsAggregator(application));

    assertThat(contextsAggregatorRepository.existsById(application.getId()))
        .isTrue();
  }

  @Test
  void should_false_if_a_contexts_aggregator_does_not_exists_by_application_id() {
    assertThat(contextsAggregatorRepository.existsById(APPLICATION_ID))
        .isFalse();
  }
}
