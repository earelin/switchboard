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

package uk.co.telegraph.switchboard.application;

import java.util.List;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.telegraph.switchboard.application.mappers.ContextMapper;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.domain.ContextsAggregator;
import uk.co.telegraph.switchboard.domain.ObjectDoesNotExists;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;
import uk.co.telegraph.switchboard.repositories.ContextsAggregatorRepository;

@RestController
@RequestMapping(
    value = "/rest/v1/application/{applicationId}/context",
    produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ContextController {

  private final ApplicationRepository applicationRepository;
  private final ContextsAggregatorRepository contextsAggregatorRepository;
  private final ContextMapper contextMapper;

  public ContextController(
      ApplicationRepository applicationRepository,
      ContextsAggregatorRepository contextsAggregatorRepository) {
    this.applicationRepository = applicationRepository;
    this.contextsAggregatorRepository = contextsAggregatorRepository;
    this.contextMapper = Mappers.getMapper(ContextMapper.class);
  }

  @GetMapping
  public List<String> getContextsList(@PathVariable String applicationId) {
    ContextsAggregator contextsAggregator = getContextAggregatorForApplicationId(applicationId);
    return contextMapper.domainMapToDto(contextsAggregator.getContexts());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public String createContext(
      @PathVariable String applicationId,
      @RequestBody String contextName) {
    ContextsAggregator contextsAggregator = getContextAggregatorForApplicationId(applicationId);
    contextsAggregator.addContext(contextName);
    contextsAggregatorRepository.save(contextsAggregator);
    return contextName;
  }

  @DeleteMapping("/{contextName}")
  public void deleteContext(
      @PathVariable String applicationId,
      @PathVariable String contextName) {
    ContextsAggregator contextsAggregator = getContextAggregatorForApplicationId(applicationId);
    try {
      contextsAggregator.removeContext(contextName);
      contextsAggregatorRepository.save(contextsAggregator);
    } catch (ObjectDoesNotExists exception) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          String.format("Context %s does not exists", contextName),
          exception
      );
    }
  }

  private ContextsAggregator getContextAggregatorForApplicationId(String applicationId) {
    return contextsAggregatorRepository.getByApplicationId(applicationId)
        .orElseGet(() -> createContextAggregatorFromApplicationId(applicationId));
  }

  private ContextsAggregator createContextAggregatorFromApplicationId(String applicationId) {
    Application application = applicationRepository.getById(applicationId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, String.format("Application %s not found.", applicationId)
        ));
    return new ContextsAggregator(application);
  }
}
