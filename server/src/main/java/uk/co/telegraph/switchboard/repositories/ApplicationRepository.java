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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.infrastructure.jpa.repositories.ApplicationJpaRepository;

@Component
public class ApplicationRepository {

  private final ApplicationJpaRepository jpaRepository;

  public ApplicationRepository(ApplicationJpaRepository jpaRepository) {
    this.jpaRepository = jpaRepository;
  }

  public Optional<Application> getById(String id) {
    return jpaRepository.findById(id);
  }

  public Application save(Application application) {
    return jpaRepository.save(application);
  }

  public void removeById(String id) {
    jpaRepository.deleteById(id);
  }

  public boolean existsById(String id) {
    return jpaRepository.existsById(id);
  }

  public Page<Application> getPagedList(Pageable pageable) {
    return jpaRepository.findAll(pageable);
  }
}
