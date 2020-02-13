package uk.co.telegraph.switchboard.dtos;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateApplicationDto implements Serializable {

  private static final long serialVersionUID = -1092331299112614892L;

  private String name;
  private String description;
}
