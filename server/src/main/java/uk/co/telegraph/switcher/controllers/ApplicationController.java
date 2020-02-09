package uk.co.telegraph.switcher.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.telegraph.switcher.services.ApplicationService;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

  private final ApplicationService applicationService;

  public ApplicationController(ApplicationService applicationService) {
    this.applicationService = applicationService;
  }

}
