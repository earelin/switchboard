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

package uk.co.telegraph.switchboard.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.Context;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;

@Service
@Transactional
public class ApplicationServiceImp implements ApplicationService {

  private final ApplicationRepository applicationRepository;
  private final SecretsGeneratorService secretsGeneratorService;

  public ApplicationServiceImp(
      ApplicationRepository applicationRepository,
      SecretsGeneratorService secretsGeneratorService) {
    this.applicationRepository = applicationRepository;
    this.secretsGeneratorService = secretsGeneratorService;
  }

  @Override
  public Application createFrom(String name, String description) {
    return create(name, description, Set.of(Context.buildDefault()));
  }

  @Override
  public Application createFrom(String name, String description, Set<String> context) {
    return null;
  }

  private Application create(String name, String description, Set<Context> context) {
    return null;
  }

  @Override
  public void delete(Application application) {

  }

  @Override
  public Application updateNameAndDescription(Application application, String name,
      String description) {
    return null;
  }

  @Override
  public Application addContext(Application application, String contextKey) {
    return null;
  }

  @Override
  public Application removeContext(Application application, String contextKey) {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Application> findByKey(String key) {
    return Optional.empty();
  }

  @Override
  @Transactional(readOnly = true)
  public List<Application> findAll() {
    return null;
  }

  @Override
  @Transactional(readOnly = true)
  public List<Application> findAll(Pageable pageable) {
    return null;
  }
}
