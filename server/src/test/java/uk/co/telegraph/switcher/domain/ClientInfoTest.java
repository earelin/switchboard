package uk.co.telegraph.switcher.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientInfoTest {

  private static final String CLIENT_INFO_INSTANCE = "mac-1435643";
  private static final String CLIENT_INFO_USER = "john.smith";

  private Application application;
  private Environment environment;
  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    clientInfo = new ClientInfo();
    clientInfo.setEnvironment(createEnvironment());
    clientInfo.setInstance(CLIENT_INFO_INSTANCE);
    clientInfo.setUser(CLIENT_INFO_USER);
  }

  @Test
  void shouldSetEnvironment() {
    assertThat(clientInfo.getEnvironment())
        .isEqualTo(environment);
  }

  @Test
  void shouldSetInstance() {
    assertThat(clientInfo.getInstance())
        .isEqualTo(CLIENT_INFO_INSTANCE);
  }

  @Test
  void shouldSetUserId() {
    assertThat(clientInfo.getUser())
        .isEqualTo(CLIENT_INFO_USER);
  }

  @Test
  void shouldReturnApplicationIfEnvironmentIsSet() {
    assertThat(clientInfo.getApplication())
        .isEqualTo(application);
  }

  @Test
  void shouldReturnNullIfEnvironmentIsNotSet() {
    clientInfo.setEnvironment(null);

    assertThat(clientInfo.getApplication())
        .isNull();
  }

  @Test
  void shouldCanEqualSameClass() {
    ClientInfo comparedObject = new ClientInfo();

    assertThat(clientInfo.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "asdf";

    assertThat(clientInfo.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(clientInfo)
        .isEqualTo(clientInfo);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(clientInfo)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToAClientInfoWithSameProperties() {
    ClientInfo compareObject = new ClientInfo();
    compareObject.setEnvironment(createEnvironment());
    compareObject.setInstance(CLIENT_INFO_INSTANCE);
    compareObject.setUser(CLIENT_INFO_USER);

    assertThat(clientInfo)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAClientInfoWithDifferentUser() {
    ClientInfo compareObject = new ClientInfo();
    compareObject.setEnvironment(createEnvironment());
    compareObject.setInstance(CLIENT_INFO_INSTANCE);
    compareObject.setUser("john.snow");

    assertThat(clientInfo)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAClientInfoWithDifferentInstance() {
    ClientInfo compareObject = new ClientInfo();
    compareObject.setEnvironment(createEnvironment());
    compareObject.setInstance("pc-5354");
    compareObject.setUser(CLIENT_INFO_USER);

    assertThat(clientInfo)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAClientInfoWithDifferentEnvironment() {
    Environment distinctEnvironment = new Environment();
    distinctEnvironment.setId(125L);

    ClientInfo compareObject = new ClientInfo();
    compareObject.setEnvironment(distinctEnvironment);
    compareObject.setInstance(CLIENT_INFO_INSTANCE);
    compareObject.setUser(CLIENT_INFO_USER);

    assertThat(clientInfo)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(clientInfo)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameObjectShouldHaveSameHashCode() {
    assertThat(clientInfo.hashCode())
        .isEqualTo(clientInfo.hashCode());
  }

  @Test
  void twoObjectWithTheSamePropertiesShouldHaveSameHashCode() {
    ClientInfo compareObject = new ClientInfo();
    compareObject.setEnvironment(createEnvironment());
    compareObject.setInstance(CLIENT_INFO_INSTANCE);
    compareObject.setUser(CLIENT_INFO_USER);

    assertThat(clientInfo.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(clientInfo.toString())
        .isEqualTo("ClientInfo("
            + "environment=" + environment.toString() + ", "
            + "instance=" + CLIENT_INFO_INSTANCE + ", "
            + "user=" + CLIENT_INFO_USER
            + ")");
  }

  private Environment createEnvironment() {
    application = new Application();
    application.setId(25L);

    environment = new Environment();
    environment.setId(12L);
    environment.setApplication(application);

    return environment;
  }
}
