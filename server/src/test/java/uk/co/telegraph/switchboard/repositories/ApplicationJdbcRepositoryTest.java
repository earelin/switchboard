package uk.co.telegraph.switchboard.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.jdbc.JdbcTestUtils;

@SpringBootTest
@Sql(
    executionPhase = ExecutionPhase.BEFORE_TEST_METHOD,
    scripts = {"/sql/application-jdbc-repository-test-suite.sql"}
)
@Tag("integration")
class ApplicationJdbcRepositoryTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private ApplicationRepository applicationRepository;

  @Test
  void shouldCountRecords() {
    assertThat(applicationRepository.count())
        .isEqualTo(5);
  }

  @Test
  void existsShouldReturnTrueIfAnApplicationExists() {
    assertThat(applicationRepository.existsByKey("editorial-dashboard"))
        .isTrue();
  }

  @Test
  void existsShouldReturnFalseIfAnApplicationDoesNotExists() {
    assertThat(applicationRepository.existsByKey("not-existing-application"))
        .isFalse();
  }

  @Test
  void findShouldReturnAnApplicationIfItDoesExists() {
    assertThat(applicationRepository.findByKey("mobile-application"))
        .isPresent()
        .get()
        .hasFieldOrPropertyWithValue("key", "mobile-application")
        .hasFieldOrPropertyWithValue("name", "Mobile Application")
        .hasFieldOrPropertyWithValue("secret", "EU8ig9DWP0O41IZO420QwumGXC1sqJYE5lwvpGzUpuMoC9oj3tDeN63qBcZCBY3h")
        .hasFieldOrPropertyWithValue("description", "Nullam ipsum orci, suscipit a nibh vel, tempus iaculis nibh.");
  }

  @Test
  void findShouldReturnEmptyIfAnApplicationDoesExists() {
    assertThat(applicationRepository.findByKey("not-existing-application"))
        .isNotPresent();
  }

  @Test
  void shouldDeleteAnApplicationIfExists() {
    applicationRepository.deleteByKey("payment-application");

    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "application"))
        .isEqualTo(4);
    assertThat(JdbcTestUtils.countRowsInTableWhere(
            jdbcTemplate, "application", "key = payment-application"))
        .isEqualTo(0);
  }

  @Test
  void shouldNotDeleteAnApplicationIfDoesNotExists() {
    applicationRepository.deleteByKey("not-existing-application");

    assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "application"))
        .isEqualTo(5);
  }

  @Test
  void shouldFindAllApplications() {
    assertThat(applicationRepository.findAll())
        .isInstanceOf(List.class)
        .hasSize(5)
        .doesNotHaveDuplicates();
  }
}
