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

import java.util.Optional;
import org.springframework.stereotype.Component;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.ContextsAggregator;
import uk.co.telegraph.switchboard.infrastructure.jpa.repositories.ContextsJpaRepository;

@Component
public class ContextsAggregatorRepository {

  private final ContextsJpaRepository jpaRepository;

  public ContextsAggregatorRepository(
      ContextsJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  public Optional<ContextsAggregator> getByApplication(Application application) {
    return jpaRepository.findByApplication(application);
  }

  public Optional<ContextsAggregator> getByApplicationId(String applicationId) {
    return jpaRepository.findById(applicationId);
  }

  public ContextsAggregator save(ContextsAggregator contextsAggregator) {
    return jpaRepository.save(contextsAggregator);
  }

  public boolean existsByApplication(Application application) {
    return jpaRepository.existsByApplication(application);
  }

  public boolean existsByApplicationId(String applicationId) {
    return jpaRepository.existsById(applicationId);
  }
}
