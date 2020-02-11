package uk.co.telegraph.switcher.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationDto {
  private String key;
  private String name;
  private String secret;
  private String description;
}
