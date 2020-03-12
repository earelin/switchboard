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
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
import uk.co.telegraph.switchboard.application.dto.ApplicationDto;
import uk.co.telegraph.switchboard.application.dto.ApplicationRequestDto;
import uk.co.telegraph.switchboard.application.dto.PageDto;
import uk.co.telegraph.switchboard.application.mappers.ApplicationMapper;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.factories.ApplicationFactory;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;

@RestController
@RequestMapping("/rest/v1/application")
public class ApplicationController {

  private static final String APPLICATION_NOT_FOUND_MESSAGE = "Application not found: %s";

  private final ApplicationFactory applicationFactory;
  private final ApplicationRepository applicationRepository;
  private final ApplicationMapper applicationMapper;
  private final SortablePageableBuilder pageableBuilder;

  public ApplicationController(
      ApplicationFactory applicationFactory,
      ApplicationRepository applicationRepository) {
    this.applicationFactory = applicationFactory;
    this.applicationRepository = applicationRepository;
    this.applicationMapper = Mappers.getMapper(ApplicationMapper.class);
    this.pageableBuilder = new SortablePageableBuilder("name");
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ApplicationDto createApplication(
      @RequestBody @Valid ApplicationRequestDto request) {
    Application application = applicationFactory.createApplication(
        request.getName(),
        request.getDescription()
    );

    applicationRepository.saveApplication(application);

    return applicationMapper.domainToDto(application);
  }

  @GetMapping
  public PageDto<ApplicationDto> getApplicationList(
      @RequestParam(defaultValue = "0", required = false) int page,
      @RequestParam(defaultValue = "20", required = false) int size,
      @RequestParam(value = "sort", defaultValue = "name", required = false) String[] sortedProperties) {
    Pageable pageable;

    try {
      pageable = pageableBuilder.buildPageable(page, size, sortedProperties);
    } catch (IllegalArgumentException exception) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          exception.getMessage()
      );
    }

    Page<Application> applicationPage = applicationRepository.getPagedApplicationList(pageable);
    return applicationMapper.domainPageToDto(applicationPage);
  }

  @GetMapping("/{id}")
  public ApplicationDto findApplication(@PathVariable String id) {
    Application application = applicationRepository.getApplication(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format(APPLICATION_NOT_FOUND_MESSAGE, id)
        ));
    return applicationMapper.domainToDto(application);
  }

  @DeleteMapping("/{id}")
  public void deleteApplication(@PathVariable String id) {
    if (!applicationRepository.doesApplicationExists(id)) {
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND,
          String.format(APPLICATION_NOT_FOUND_MESSAGE, id)
      );
    }
    applicationRepository.removeApplication(id);
  }

  @PutMapping("/{id}")
  public ApplicationDto updateApplication(
      @PathVariable String id,
      @RequestBody @Valid ApplicationRequestDto request) {
    Application application = applicationRepository.getApplication(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format(APPLICATION_NOT_FOUND_MESSAGE, id)
        ));

    applicationMapper.updateDomainFromDto(request, application);
    applicationRepository.saveApplication(application);

    return applicationMapper.domainToDto(application);
  }
}
