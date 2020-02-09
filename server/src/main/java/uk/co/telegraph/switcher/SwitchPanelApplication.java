package uk.co.telegraph.switcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SwitchPanelApplication {

  public static void main(String[] args) {
    SpringApplication.run(SwitchPanelApplication.class, args);
  }

}
