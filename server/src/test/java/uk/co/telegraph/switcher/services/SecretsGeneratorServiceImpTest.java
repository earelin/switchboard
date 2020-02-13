package uk.co.telegraph.switcher.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecretsGeneratorServiceImpTest {

  private SecretsGeneratorServiceImp keyService;

  @BeforeEach
  void setUp() throws NoSuchAlgorithmException {
    keyService = new SecretsGeneratorServiceImp();
  }

  @Test
  void shouldGenerateSecret() {
    String key = keyService.generateSecret(64);

    assertThat(key)
      .matches(Pattern.compile("^[A-Za-z0-9]*$"))
      .hasSize(64);
  }
}
