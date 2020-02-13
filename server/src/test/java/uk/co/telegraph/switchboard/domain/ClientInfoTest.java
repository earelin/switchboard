package uk.co.telegraph.switchboard.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientInfoTest {

  private static final String APPLICATION_KEY = "newsroom-dasboard";
  private static final String CONTEXT_KEY = "production";
  private static final String INSTANCE = "mac-1435643";
  private static final String USER = "john.smith";
  private static final ZonedDateTime DATE_TIME = ZonedDateTime.now();

  private ClientInfo clientInfo;

  @BeforeEach
  void setUp() {
    clientInfo = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME)
        .build();
  }

  @Test
  void shouldSetApplicationKeyWithBuilder() {
    assertThat(clientInfo.getApplication())
        .isEqualTo(APPLICATION_KEY);
  }

  @Test
  void shouldSetEnvironmentKeyWithBuilder() {
    assertThat(clientInfo.getContext())
        .isEqualTo(CONTEXT_KEY);
  }

  @Test
  void shouldSetInstanceWithBuilder() {
    assertThat(clientInfo.getInstance())
        .isEqualTo(INSTANCE);
  }

  @Test
  void shouldSetUserWithBuilder() {
    assertThat(clientInfo.getUser())
        .isEqualTo(USER);
  }

  @Test
  void shouldSetDateTimeWithBuilder() {
    assertThat(clientInfo.getDateTime())
        .isEqualTo(DATE_TIME);
  }

  @Test
  void shouldCanEqualSameClass() {
    ClientInfo comparedObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .build();

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
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAClientInfoWithDifferentApplicationKey() {
    ClientInfo compareObject = ClientInfo.builder()
        .application("other-application")
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAClientInfoWithDifferentEnvironmentKey() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context("development")
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAClientInfoWithDifferentInstance() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance("pc-1354")
        .user(USER)
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAClientInfoWithDifferentUser() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user("john.snow")
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToAClientInfoWithDifferentDate() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME.plusHours(5))
        .build();

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
  void sameObjectInstanceShouldHaveSameHashCode() {
    assertThat(clientInfo.hashCode())
        .isEqualTo(clientInfo.hashCode());
  }

  @Test
  void twoObjectWithTheSamePropertiesShouldHaveSameHashCode() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentApplicationKeyShouldHaveDifferentHashCode() {
    ClientInfo compareObject = ClientInfo.builder()
        .application("other-application")
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentEnvironmentKeyShouldHaveDifferentHashCode() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context("development")
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentInstanceShouldHaveDifferentHashCode() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance("pc-1354")
        .user(USER)
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentUserShouldHaveDifferentHashCode() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user("john.snow")
        .dateTime(DATE_TIME)
        .build();

    assertThat(clientInfo.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentDateTimeShouldHaveDifferentHashCode() {
    ClientInfo compareObject = ClientInfo.builder()
        .application(APPLICATION_KEY)
        .context(CONTEXT_KEY)
        .instance(INSTANCE)
        .user(USER)
        .dateTime(DATE_TIME.plusHours(5))
        .build();

    assertThat(clientInfo.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(clientInfo.toString())
        .isEqualTo("ClientInfo("
            + "application=" + APPLICATION_KEY + ", "
            + "context=" + CONTEXT_KEY + ", "
            + "instance=" + INSTANCE + ", "
            + "user=" + USER + ", "
            + "dateTime=" + DATE_TIME.toString()
            + ")");
  }
}
