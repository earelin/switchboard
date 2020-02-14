package uk.co.telegraph.switchboard.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.co.telegraph.switchboard.domain.Application;

@Repository
@Slf4j
public class ApplicationJdbcRepository implements ApplicationRepository {

  private final JdbcTemplate jdbcTemplate;

  public ApplicationJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Application> findByKey(String key) {
    Application application = null;

    try {
      application = jdbcTemplate.queryForObject(
          "SELECT * FROM application WHERE key = ?",
          ApplicationJdbcRepository::mapRowToApplication,
          key
      );
    } catch (EmptyResultDataAccessException emptyResult) {
      log.warn("Could not find application: {}", key);
    }

    return Optional.ofNullable(application);
  }

  @Override
  public boolean existsByKey(String key) {
    Integer result = jdbcTemplate
        .queryForObject("SELECT count(*) FROM application WHERE key = ?", Integer.class, key);
    return result != 0;
  }

  @Override
  public long count() {
    return jdbcTemplate
        .queryForObject("SELECT count(*) FROM application", Long.class);
  }

  @Override
  public void create(Application application) {

  }

  @Override
  public void updateByKey(Application application, String key) {

  }

  @Override
  public void update(Application application) {

  }

  @Override
  public void deleteByKey(String key) {
    jdbcTemplate.update("DELETE FROM application WHERE key = ?", key);
  }

  @Override
  public List<Application> findAll() {
    return jdbcTemplate.query(
        "SELECT * FROM application ORDER BY name ASC",
        ApplicationJdbcRepository::mapRowToApplication
    );
  }

  @Override
  public Page<Application> findAll(Pageable pageable) {
    return null;
  }

  private static Application mapRowToApplication(ResultSet rs, int rowNum) throws SQLException {
    Application application = new Application(rs.getString("key"));
    application.setName(rs.getString("name"));
    application.setSecret(rs.getString("secret"));
    application.setDescription(rs.getString("description"));
    return application;
  }
}
