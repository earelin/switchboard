package uk.co.telegraph.switcher.dtos;

import java.io.Serializable;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateApplicationDto implements Serializable {

  private static final long serialVersionUID = 1616804722855966764L;

  private String name;
  private String description;
  private Set<String> contexts;
}
