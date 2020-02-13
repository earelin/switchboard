package uk.co.telegraph.switcher.domain.strategy;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.ZonedDateTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.co.telegraph.switcher.domain.ClientInfo;

class UserGroupStrategyTest {

  private static final long ID = 25;

  private UserGroupStrategy userGroupStrategy;

  @BeforeEach
  void setUp() {
    userGroupStrategy = new UserGroupStrategy(ID);
    userGroupStrategy.setUserGroup(generateUserGroup());
  }

  @Test
  void shouldSetIdOnConstructor() {
    assertThat(userGroupStrategy.getId())
        .isEqualTo(ID);
  }

  @Test
  void shouldSetUsers() {
    assertThat(userGroupStrategy.getUserGroup())
        .isEqualTo(generateUserGroup());
  }

  @Test
  void shouldReturnIsEnabledFalseIfUserGroupIsNotSet() {
    userGroupStrategy.setUserGroup(null);
    ClientInfo clientInfo = ClientInfo.builder()
      .applicationKey("newsroom-dashboard")
      .environmentKey("production")
      .instance("pc-3456")
      .dateTime(ZonedDateTime.now())
      .user("gandalf")
      .build();

    assertThat(userGroupStrategy.isEnabled(clientInfo))
        .isFalse();
  }

  @Test
  void shouldReturnIsEnabledTrueIfUserGroupContainsClientInfoUser() {
    ClientInfo clientInfo = ClientInfo.builder()
        .applicationKey("newsroom-dashboard")
        .environmentKey("production")
        .instance("pc-3456")
        .dateTime(ZonedDateTime.now())
        .user("gandalf")
        .build();

    assertThat(userGroupStrategy.isEnabled(clientInfo))
        .isTrue();
  }

  @Test
  void shouldReturnIsEnabledFalseIfUserGroupNotContainsClientInfoUser() {
    ClientInfo clientInfo = ClientInfo.builder()
        .applicationKey("newsroom-dashboard")
        .environmentKey("production")
        .instance("pc-3456")
        .dateTime(ZonedDateTime.now())
        .user("sauron")
        .build();

    assertThat(userGroupStrategy.isEnabled(clientInfo))
        .isFalse();
  }

  @Test
  void shouldCanEqualSameClass() {
    UserGroupStrategy comparedObject = new UserGroupStrategy(12);

    assertThat(userGroupStrategy.canEqual(comparedObject))
        .isTrue();
  }

  @Test
  void shouldNotCanEqualDifferentClass() {
    String comparedObject = "string";

    assertThat(userGroupStrategy.canEqual(comparedObject))
        .isFalse();
  }

  @Test
  void shouldBeEqualToItself() {
    assertThat(userGroupStrategy)
        .isEqualTo(userGroupStrategy);
  }

  @Test
  void shouldNotBeEqualToNull() {
    assertThat(userGroupStrategy)
        .isNotEqualTo(null);
  }

  @Test
  void shouldBeEqualToAUserGroupStrategyWithSameId() {
    UserGroupStrategy compareObject = new UserGroupStrategy(ID);

    assertThat(userGroupStrategy)
        .isEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADefaultStrategyWithADifferentId() {
    UserGroupStrategy compareObject = new UserGroupStrategy(12);
    compareObject.setUserGroup(generateUserGroup());

    assertThat(userGroupStrategy)
        .isNotEqualTo(compareObject);
  }

  @Test
  void shouldNotBeEqualToADifferentClass() {
    String compareObject = "testing";

    assertThat(userGroupStrategy)
        .isNotEqualTo(compareObject);
  }

  @Test
  void sameInstanceShouldHaveSameHashCode() {
    assertThat(userGroupStrategy.hashCode())
        .isEqualTo(userGroupStrategy.hashCode());
  }

  @Test
  void twoUserGroupStrategiesWithTheSameIdShouldHaveSameHashCode() {
    UserGroupStrategy compareObject = new UserGroupStrategy(ID);

    assertThat(userGroupStrategy.hashCode())
        .isEqualTo(compareObject.hashCode());
  }

  @Test
  void twoObjectWithDifferentIdShouldHaveDifferentHashCode() {
    UserGroupStrategy compareObject = new UserGroupStrategy(12);
    compareObject.setUserGroup(generateUserGroup());

    assertThat(userGroupStrategy.hashCode())
        .isNotEqualTo(compareObject.hashCode());
  }

  @Test
  void shouldConvertToString() {
    assertThat(userGroupStrategy.toString())
        .isEqualTo("UserGroupStrategy("
            + "super=Strategy(id=" + ID + "), "
            + "userGroup=" + generateUserGroup().toString()
            + ")");
  }

  private UserGroup generateUserGroup() {
    UserGroup userGroup = new UserGroup("beta-users");
    userGroup.setName("Beta users");
    userGroup.setUsers(Set.of("frodo", "sam", "gandalf"));
    return userGroup;
  }

}
