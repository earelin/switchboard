package uk.co.telegraph.switcher.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateApplicationDto {
  private String name;
  private String description;
}
