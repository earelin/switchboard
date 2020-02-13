package uk.co.telegraph.switcher.dtos;

import java.io.Serializable;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationDto implements Serializable {

  private static final long serialVersionUID = 7524449056283565095L;

  private String key;
  private String name;
  private String secret;
  private String description;
  private Set<String> contexts;
}
