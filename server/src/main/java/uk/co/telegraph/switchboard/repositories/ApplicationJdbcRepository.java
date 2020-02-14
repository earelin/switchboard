package uk.co.telegraph.switchboard.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import uk.co.telegraph.switchboard.domain.Application;

@Repository
public class ApplicationJdbcRepository implements ApplicationRepository {

  private final JdbcTemplate jdbcTemplate;

  public ApplicationJdbcRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public Optional<Application> findByKey(String key) {
    return Optional.empty();
  }

  @Override
  public boolean existsByKey(String key) {
    Integer result = jdbcTemplate.queryForObject("select count(*) from application where key = ?",
        Integer.class, key);
    return result == 1;
  }

  @Override
  public long count() {
    return jdbcTemplate.queryForObject("select count(*) from application;", Long.class);
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

  }

  @Override
  public List<Application> findAll() {
    return null;
  }

  @Override
  public Page<Application> findAll(Pageable pageable) {
    return null;
  }
}
