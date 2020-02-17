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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {
//
//  private final ApplicationService applicationService;
//  private final ModelMapper modelMapper;
//
//  public ApplicationController(
//      ApplicationService applicationService,
//      ModelMapper modelMapper) {
//    this.applicationService = applicationService;
//    this.modelMapper = modelMapper;
//  }
//
//  @GetMapping("/{key}")
//  public ApplicationDto find(@PathVariable String key) {
//    Application application = applicationService.find(key)
//        .orElseThrow(() -> new ResponseStatusException(
//            HttpStatus.NOT_FOUND,
//            String.format("Application not found: %s", key)
//        ));
//    return convertToDto(application);
//  }
//
//  @GetMapping
//  public List<ApplicationDto> findAll() {
//    return applicationService.findAll()
//        .stream()
//        .map(this::convertToDto)
//        .collect(Collectors.toList());
//  }
//
//  private ApplicationDto convertToDto(Application application) {
//    return modelMapper.map(application, ApplicationDto.class);
//  }
}
