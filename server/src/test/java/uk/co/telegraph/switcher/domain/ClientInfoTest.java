package uk.co.telegraph.switcher.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switcher.domain.ClientInfo;

class ClientInfoTest {

  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    clientInfo = new ClientInfo();
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

}
