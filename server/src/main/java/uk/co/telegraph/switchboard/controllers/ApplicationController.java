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
