package uk.co.telegraph.switcher.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SecretsServiceImpTest {

  private SecretsServiceImp keyService;

  @BeforeEach
  void setUp() throws NoSuchAlgorithmException {
    keyService = new SecretsServiceImp();
  }

  @Test
  void shouldGenerateSecret() {
    String key = keyService.generateSecret();

    assertThat(key, matchesPattern("^[-A-Za-z0-9+/=]*$"));
  }
}
