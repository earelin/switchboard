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
 *
 */

package uk.co.telegraph.switchboard.controllers;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import uk.co.telegraph.switchboard.controllers.dto.ApplicationDto;
import uk.co.telegraph.switchboard.domain.Application;
import uk.co.telegraph.switchboard.factories.ApplicationFactory;
import uk.co.telegraph.switchboard.repositories.ApplicationRepository;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

  private final ApplicationRepository applicationRepository;
  private final ApplicationFactory applicationFactory;
  private final ModelMapper modelMapper;

  public ApplicationController(
      ApplicationRepository applicationRepository,
      ApplicationFactory applicationFactory,
      ModelMapper modelMapper
  ) {
    this.applicationRepository = applicationRepository;
    this.applicationFactory = applicationFactory;
    this.modelMapper = modelMapper;
  }

  @GetMapping("/{id}")
  public ApplicationDto find(@PathVariable String id) {
    Application application = applicationRepository.findById(id)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND,
            String.format("Application not found: %s", id)
        ));
    return convertToDto(application);
  }

  @GetMapping
  public List<ApplicationDto> findAll() {
    return applicationRepository.findAllByOrderByNameAsc()
        .stream()
        .map(this::convertToDto)
        .collect(Collectors.toList());
  }

  private ApplicationDto convertToDto(Application application) {
    return modelMapper.map(application, ApplicationDto.class);
  }
}
