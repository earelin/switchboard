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

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import uk.co.telegraph.switchboard.domain.model.Application;
import uk.co.telegraph.switchboard.domain.repositories.ApplicationRepository;
import uk.co.telegraph.switchboard.infrastructure.repository.dao.ApplicationDao;

@Repository
public class ApplicationInMemoryRepository implements ApplicationRepository {

  private final Map<String, ApplicationDao> applications;

  public ApplicationInMemoryRepository() {
    applications = new ConcurrentHashMap<>();
  }

  public ApplicationInMemoryRepository(Collection<ApplicationDao> applicationsSet) {
    Map<String, ApplicationDao> applicationsMap = applicationsSet.stream()
        .collect(Collectors.toMap(
            application -> application.getId(),
            application -> application));
    applications = new ConcurrentHashMap<>(applicationsMap);
  }

  @Override
  public Optional<Application> getById(String id) {
    return Optional.empty();
  }

  @Override
  public Application save(Application application) {
    return null;
  }

  @Override
  public void removeById(String id) {

  }

  @Override
  public boolean existsById(String id) {
    return false;
  }

  @Override
  public Page<Application> getPagedList(Pageable pageable) {
    return null;
  }
}
