package uk.co.telegraph.switcher.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientInfoTest {

  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    clientInfo = new ClientInfo();
  }

  @Test
  void shouldSetApplication() {
    Application application = new Application();
    application.setId("newsroom-dashboard");

    clientInfo.setApplication(application);

    assertEquals(application, clientInfo.getApplication());
  }

  @Test
  void shouldSetInstance() {
    clientInfo.setInstance("drd-125");

    assertEquals("drd-125", clientInfo.getInstance());
  }

  @Test
  void shouldSetUserId() {
    clientInfo.setUserId("jcarriba");

    assertEquals("jcarriba", clientInfo.getUserId());
  }

  @Test
  void shouldSetEnvironment() {
    clientInfo.setEnvironment("prod");

    assertEquals("prod", clientInfo.getEnvironment());
  }

}
