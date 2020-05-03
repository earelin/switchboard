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

import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.telegraph.switchboard.application.dto.ApplicationCreateDto;
import uk.co.telegraph.switchboard.application.dto.ApplicationDto;
import uk.co.telegraph.switchboard.application.dto.PageDto;
import uk.co.telegraph.switchboard.application.mappers.ApplicationDtoMapper;
import uk.co.telegraph.switchboard.domain.factories.ApplicationFactory;
import uk.co.telegraph.switchboard.domain.model.Application;
import uk.co.telegraph.switchboard.domain.repositories.ApplicationRepository;

@RestController
@RequestMapping(
    value = "/rest/v1/application",
    produces = {MediaType.APPLICATION_JSON_VALUE}
)
public class ApplicationController {

  private static final String APPLICATION_NOT_FOUND_MESSAGE = "Application not found: %s";

  private final ApplicationFactory applicationFactory;
  private final ApplicationRepository applicationRepository;
  private final ApplicationDtoMapper applicationDtoMapper;
  private final SortablePageableBuilder pageableBuilder;

  public ApplicationController(
      ApplicationFactory applicationFactory,
      ApplicationRepository applicationRepository,
      ApplicationDtoMapper applicationDtoMapper) {
    this.applicationFactory = applicationFactory;
    this.applicationRepository = applicationRepository;
    this.applicationDtoMapper = applicationDtoMapper;
    this.pageableBuilder = new SortablePageableBuilder("name");
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApplicationDto createApplication(
      @RequestBody @Valid ApplicationCreateDto applicationData) {
    Application application =
        applicationFactory.createApplication(
            applicationData.getName(), applicationData.getDescription());

    Application savedApplication = applicationRepository.save(application);

    return applicationDtoMapper.domainToDto(savedApplication);
  }

  @GetMapping
  public PageDto<ApplicationDto> getApplicationList(
      @RequestParam(defaultValue = "0", required = false) int page,
      @RequestParam(defaultValue = "20", required = false) int size,
      @RequestParam(value = "sort", defaultValue = "name", required = false)
          String[] sortedProperties) {
    Pageable pageable;

    try {
      pageable = pageableBuilder.buildPageable(page, size, sortedProperties);
    } catch (IllegalArgumentException exception) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage(), exception);
    }

    Page<Application> applicationPage = applicationRepository.getPagedList(pageable);
    return applicationDtoMapper.domainPageToDto(applicationPage);
  }

  @GetMapping("/{id}")
  public ApplicationDto findApplication(@PathVariable String id) {
    Application application =
        applicationRepository
            .getById(id)
            .orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format(APPLICATION_NOT_FOUND_MESSAGE, id)));
    return applicationDtoMapper.domainToDto(application);
  }

  @DeleteMapping("/{id}")
  public void deleteApplication(@PathVariable String id) {
    if (!applicationRepository.existsById(id)) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND, String.format(APPLICATION_NOT_FOUND_MESSAGE, id));
    }
    applicationRepository.removeById(id);
  }

  @PutMapping("/{id}")
  public ApplicationDto updateApplication(
      @PathVariable String id, @RequestBody @Valid ApplicationCreateDto request) {
    Application application =
        applicationRepository
            .getById(id)
            .orElseThrow(
                () -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format(APPLICATION_NOT_FOUND_MESSAGE, id)));

    applicationDtoMapper.updateDomainFromDto(request, application);
    Application savedApplication = applicationRepository.save(application);

    return applicationDtoMapper.domainToDto(savedApplication);
  }
}
